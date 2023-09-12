package ac.kr.tukorea.capstone.post.repository;

import ac.kr.tukorea.capstone.post.entity.QLikePost;
import ac.kr.tukorea.capstone.post.entity.QPost;
import ac.kr.tukorea.capstone.post.entity.QPostImage;
import ac.kr.tukorea.capstone.post.vo.PostVo;
import ac.kr.tukorea.capstone.product.entity.QProduct;
import ac.kr.tukorea.capstone.user.entity.QUser;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

@RequiredArgsConstructor
@Repository
public class PostRepositoryImpl implements PostRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    private final QPost post = QPost.post;
    private final QUser user = QUser.user;
    private final QProduct product = QProduct.product;
    private final QPostImage postImage = QPostImage.postImage;
    private final QLikePost likePost = QLikePost.likePost;

    @Override
    public List<PostVo> getSearchedPostList(Long productId, String username, String postTitle, String postContent, String isOnSale) {
        List<PostVo> posts = jpaQueryFactory
                .selectFrom(post)
                .innerJoin(user)
                .on(post.user.eq(user))
                .innerJoin(this.product)
                .on(post.product.eq(this.product))
                .innerJoin(postImage)
                .on(postImage.post.eq(post))
                .where(eqProductId(productId), containPostTitleOrContent(postTitle), isOnSale(isOnSale))
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
                          JPAExpressions.selectOne().from(likePost).where(likePost.post.eq(post), likePost.user.username.eq(username)).limit(1))));

        return posts;
    }
    private BooleanExpression eqUsername(String username){
        if(username == null) return null;
        return user.username.eq(username);
    }

    private BooleanExpression eqProductId(Long productId){
        if(productId == null) return null;
        return post.product.id.eq(productId);
    }

    private BooleanExpression containPostTitleOrContent(String str){
        if(str == null) return null;
        return post.postTitle.contains(str).or(post.postContent.contains(str));
    }

    private BooleanExpression isOnSale(String isOnSale){
        if(isOnSale == null || isOnSale.equals("ALL") || isOnSale.equals("All") || isOnSale.equals("all")) return null;
        return post.isOnSales.eq(isOnSale);
    }
}
