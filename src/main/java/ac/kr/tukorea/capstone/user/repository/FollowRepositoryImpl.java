package ac.kr.tukorea.capstone.user.repository;

import ac.kr.tukorea.capstone.user.entity.QFollow;
import ac.kr.tukorea.capstone.user.entity.QUser;
import ac.kr.tukorea.capstone.user.entity.User;
import ac.kr.tukorea.capstone.user.vo.UserVo;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class FollowRepositoryImpl implements FollowRepositoryCustom{
    private QUser user = QUser.user;
    private QFollow follow = QFollow.follow;

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<UserVo> getFollowingList(String username) {
        List<UserVo> follows = jpaQueryFactory
                .select(Projections.bean(UserVo.class, user.id, user.username, user.nickname, user.imagePath.as("image")))
                .from(user)
                .where(user.id.eq(
                        JPAExpressions
                                .select(follow.followedUser.id)
                                .from(follow)
                                .innerJoin(user)
                                .on(user.username.eq(username))
                                .where(user.id.eq(follow.followingUser.id)))
                )
                .fetch();

        return follows;
    }

    @Override
    public List<UserVo> getFollowerList(String username) {
        List<UserVo> follows = jpaQueryFactory
                .select(Projections.bean(UserVo.class, user.id, user.username, user.nickname, user.imagePath))
                .from(user)
                .where(user.id.eq(
                        JPAExpressions
                                .select(follow.followingUser.id)
                                .from(follow)
                                .innerJoin(user)
                                .on(user.username.eq(username))
                                .where(user.id.eq(follow.followedUser.id)))
                )
                .fetch();

        return follows;
    }

    @Override
    public void deleteFollow(User followingUser, User followerUser) {
        jpaQueryFactory.delete(follow).where(follow.followingUser.eq(followingUser), follow.followedUser.eq(followerUser)).execute();
    }
}
