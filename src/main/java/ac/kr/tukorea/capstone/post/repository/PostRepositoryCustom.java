package ac.kr.tukorea.capstone.post.repository;

import ac.kr.tukorea.capstone.post.entity.Post;
import ac.kr.tukorea.capstone.post.vo.PostVo;
import ac.kr.tukorea.capstone.product.entity.Product;
import ac.kr.tukorea.capstone.user.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepositoryCustom {
    List<PostVo> getSearchedPostList(Product product, String postTitle, String postContent, String isOnSale);
}
