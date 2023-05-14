package ac.kr.tukorea.capstone.market.repository;

import ac.kr.tukorea.capstone.market.entity.Post;
import ac.kr.tukorea.capstone.market.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {
    List<PostImage> findByPost(Post post);
}
