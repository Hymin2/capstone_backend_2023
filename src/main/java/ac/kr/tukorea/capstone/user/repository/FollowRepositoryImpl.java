package ac.kr.tukorea.capstone.user.repository;

import ac.kr.tukorea.capstone.user.entity.QFollow;
import ac.kr.tukorea.capstone.user.entity.QUser;
import ac.kr.tukorea.capstone.user.entity.User;
import ac.kr.tukorea.capstone.user.vo.UserVo;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class FollowRepositoryImpl implements FollowRepositoryCustom{
    private QUser user = QUser.user;
    private QFollow follow = QFollow.follow;

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<UserVo> getFollowList(User user) {
        List<UserVo> follows = jpaQueryFactory
                .select(Projections.bean(UserVo.class, follow.followedUser, this.user.username, this.user.imagePath))
                .from(follow)
                .innerJoin(this.user)
                .on(follow.followingUser.eq(user))
                .where(this.user.eq(user))
                .fetch();

        return follows;
    }
}
