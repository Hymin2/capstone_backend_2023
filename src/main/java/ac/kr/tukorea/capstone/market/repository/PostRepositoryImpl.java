package ac.kr.tukorea.capstone.market.repository;

import ac.kr.tukorea.capstone.market.entity.Post;
import ac.kr.tukorea.capstone.market.entity.QPost;
import ac.kr.tukorea.capstone.market.entity.QPostImage;
import ac.kr.tukorea.capstone.market.vo.PostVo;
import ac.kr.tukorea.capstone.product.entity.Product;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.support.Querydsl;

import java.util.List;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    private final QPost post = QPost.post;

    @Override
    public List<Post> getSalePostList(Product product, String postTitle, String postContent, String isOnSale) {
        List<Post> posts = jpaQueryFactory
                .selectFrom(post)
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
