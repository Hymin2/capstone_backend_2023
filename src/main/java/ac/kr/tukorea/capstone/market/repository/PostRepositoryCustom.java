package ac.kr.tukorea.capstone.market.repository;

import ac.kr.tukorea.capstone.market.entity.Post;
import ac.kr.tukorea.capstone.product.entity.Product;

import java.util.List;

public interface PostRepositoryCustom {
    List<Post> getSalePostList(Product product, String postTitle, String postContent, String isOnSale);
}
