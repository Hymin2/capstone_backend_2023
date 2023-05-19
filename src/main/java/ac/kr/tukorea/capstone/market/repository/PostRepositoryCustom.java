package ac.kr.tukorea.capstone.market.repository;

import ac.kr.tukorea.capstone.market.entity.Post;
import ac.kr.tukorea.capstone.market.vo.PostVo;
import ac.kr.tukorea.capstone.product.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepositoryCustom {
    List<Post> getSalePostList(Product product, String postTitle, String postContent, String isOnSale);
}
