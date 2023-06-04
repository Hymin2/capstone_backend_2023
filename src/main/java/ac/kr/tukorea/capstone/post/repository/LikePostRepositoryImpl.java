package ac.kr.tukorea.capstone.post.repository;

import ac.kr.tukorea.capstone.post.entity.QLikePost;
import ac.kr.tukorea.capstone.post.entity.QPost;
import ac.kr.tukorea.capstone.post.entity.QPostImage;
import ac.kr.tukorea.capstone.post.vo.PostVo;
import ac.kr.tukorea.capstone.product.entity.QProduct;
import ac.kr.tukorea.capstone.user.entity.QUser;
import ac.kr.tukorea.capstone.user.entity.User;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

@Component
@RequiredArgsConstructor
public class LikePostRepositoryImpl implements LikePostRepositoryCustom {
    private QPost post = QPost.post;
    private QPostImage postImage = QPostImage.postImage;
    private QUser user = QUser.user;
    private QLikePost likePost = QLikePost.likePost;
    private QProduct product = QProduct.product;
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public List<PostVo> getLikePostList(String username) {
        List<PostVo> posts = jpaQueryFactory
                .selectFrom(post)
                .innerJoin(postImage).on(postImage.post.eq(post))
                .innerJoin(likePost).on(likePost.post.eq(post))
                .innerJoin(user).on(likePost.user.eq(user))
                .innerJoin(product).on(post.product.eq(product))
                .where(user.username.eq(username))
                .orderBy(post.id.desc())
                .distinct()
                .transform(groupBy(post.id).list(
                        Projections.constructor(PostVo.class,
                                post.id,
                                user.username,
                                user.nickname,
                                user.imagePath,
                                this.product.productName,
                                post.postTitle,
                                post.postContent,
                                post.isOnSales,
                                post.price,
                                post.postCreatedTime,
                                list(Projections.constructor(String.class, postImage.imagePath)),
                                JPAExpressions.selectOne().from(likePost).where(likePost.post.eq(post), likePost.user.eq(user)).limit(1))));
        return posts;
    }

    @Override
    public void deleteLikePost(long postId, User user) {
        jpaQueryFactory.delete(likePost).where(likePost.post.id.eq(postId), likePost.user.eq(user)).execute();
    }
}
