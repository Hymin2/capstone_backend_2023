package ac.kr.tukorea.capstone.user.repository;

import ac.kr.tukorea.capstone.post.entity.QPost;
import ac.kr.tukorea.capstone.user.dto.UserInfoDto;
import ac.kr.tukorea.capstone.user.entity.QFollow;
import ac.kr.tukorea.capstone.user.entity.QUser;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepositoryCustom{
    private QUser user = QUser.user;
    private QFollow follow = QFollow.follow;
    private QPost post = QPost.post;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public UserInfoDto getOtherUserInfo(String username, String otherUsername) {
        if(otherUsername == null) otherUsername = username;

        UserInfoDto userInfoDto = jpaQueryFactory.select(
                Projections.fields(UserInfoDto.class,
                        user.id.as("userId"),
                        user.username,
                        user.nickname,
                        user.imagePath.as("image"),
                        ExpressionUtils.as(getCountUserPost("N"), "soldOut"),
                        ExpressionUtils.as(getCountUserPost("Y"), "onSale"),
                        ExpressionUtils.as(getCountFollowingUser(), "followingNum"),
                        ExpressionUtils.as(getCountFollowedUser(), "followNum"),
                        ExpressionUtils.as(isFollow(username), "isFollow")
                )).from(user).where(user.username.eq(otherUsername)).fetchOne();

        return userInfoDto;
    }

    public JPQLQuery<Integer> getCountUserPost(String isOnSale){
        return JPAExpressions.select(post.id.count().intValue()).from(post).where(post.user.eq(user), post.isOnSales.eq(isOnSale));
    }

    public JPQLQuery<Integer> getCountFollowingUser(){
        return JPAExpressions.select(follow.id.count().intValue()).from(follow).where(follow.followingUser.eq(user));
    }

    public JPQLQuery<Integer> getCountFollowedUser(){
        return JPAExpressions.select(follow.id.count().intValue()).from(follow).where(follow.followedUser.eq(user));
    }

    public BooleanExpression isFollow(String username){
        return JPAExpressions.select(follow.id.count().intValue()).from(follow).where(follow.followingUser.username.eq(username), follow.followedUser.eq(user)).limit(1L).exists();
    }
}
