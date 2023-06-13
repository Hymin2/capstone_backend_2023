package ac.kr.tukorea.capstone.post.repository;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import ac.kr.tukorea.capstone.post.entity.Post;
import ac.kr.tukorea.capstone.post.entity.QLikePost;
import ac.kr.tukorea.capstone.post.entity.QPost;
import ac.kr.tukorea.capstone.post.entity.QPostImage;
import ac.kr.tukorea.capstone.post.vo.PostVo;
import ac.kr.tukorea.capstone.product.entity.Product;
import ac.kr.tukorea.capstone.product.entity.QProduct;
import ac.kr.tukorea.capstone.product.entity.QUsedProductPrice;
import ac.kr.tukorea.capstone.user.entity.QUser;
import ac.kr.tukorea.capstone.user.entity.User;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    private final QPost post = QPost.post;
    private final QUser user = QUser.user;
    private final QProduct product = QProduct.product;
    private final QPostImage postImage = QPostImage.postImage;
    private final QLikePost likePost = QLikePost.likePost;

    @Override
    public List<PostVo> getSearchedPostList(Product product, String username, String postTitle, String postContent, String isOnSale) {
        List<PostVo> posts = jpaQueryFactory
                .selectFrom(post)
                .innerJoin(user)
                .on(post.user.eq(user))
                .innerJoin(this.product)
                .on(post.product.eq(this.product))
                .innerJoin(postImage)
                .on(postImage.post.eq(post))
                .where(eqProduct(product), eqUsername(username), containPostTitle(postTitle), containsPostContent(postContent), isOnSale(isOnSale))
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
    private BooleanExpression eqUsername(String username){
        if(username == null) return null;
        return user.username.eq(username);
    }

    private BooleanExpression eqProduct(Product product){
        if(product == null) return null;
        return post.product.eq(product);
    }

    private BooleanExpression containPostTitle(String postTitle){
        if(postTitle == null) return null;
        return post.postTitle.contains(postTitle);
    }

    private BooleanExpression containsPostContent(String postContent){
        if(postContent == null) return null;
        return post.postContent.contains(postContent);
    }

    private BooleanExpression isOnSale(String isOnSale){
        if(isOnSale == null || isOnSale.equals("ALL") || isOnSale.equals("All") || isOnSale.equals("all")) return null;
        return post.isOnSales.eq(isOnSale);
    }
}
