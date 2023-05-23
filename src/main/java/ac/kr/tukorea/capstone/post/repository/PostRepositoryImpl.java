package ac.kr.tukorea.capstone.post.repository;

import ac.kr.tukorea.capstone.post.entity.Post;
import ac.kr.tukorea.capstone.post.entity.QPost;
import ac.kr.tukorea.capstone.post.vo.PostVo;
import ac.kr.tukorea.capstone.product.entity.Product;
import ac.kr.tukorea.capstone.product.entity.QUsedProductPrice;
import ac.kr.tukorea.capstone.user.entity.QUser;
import ac.kr.tukorea.capstone.user.entity.User;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    private final QPost post = QPost.post;
    private final QUser user = QUser.user;

    @Override
    public List<PostVo> getSearchedPostList(Product product, String postTitle, String postContent, String isOnSale) {
        List<PostVo> posts = jpaQueryFactory
                .select(Projections.bean(PostVo.class, post.id, user.username, user.imagePath, post.postTitle, post.postContent, post.isOnSales, post.postCreatedTime))
                .from(post)
                .innerJoin(user)
                .on(post.user.eq(user))
                .where(eqProduct(product), containPostTitle(postTitle), containsPostContent(postContent), isOnSale(isOnSale))
                .fetch();

        return posts;
    }

    private BooleanExpression eqProduct(Product product){
        if(product == null) return null;
        return post.product.eq(product);
    }

    private BooleanExpression containPostTitle(String postTitle){
        if(postTitle == null) return null;
        return post.postTitle.contains("%" + postTitle + "%");
    }

    private BooleanExpression containsPostContent(String postContent){
        if(postContent == null) return null;
        return post.postContent.contains("%" + postContent + "%");
    }

    private BooleanExpression isOnSale(String isOnSale){
        if(isOnSale == null || isOnSale.equals("ALL") || isOnSale.equals("All") || isOnSale.equals("all")) return null;
        return post.isOnSales.eq(isOnSale);
    }
}
