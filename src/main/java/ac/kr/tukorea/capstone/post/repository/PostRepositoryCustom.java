package ac.kr.tukorea.capstone.post.repository;

import ac.kr.tukorea.capstone.post.vo.PostVo;
import ac.kr.tukorea.capstone.product.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepositoryCustom {
    List<PostVo> getSearchedPostList(Long productId, String username, String postTitle, String postContent, String isOnSale);
}
