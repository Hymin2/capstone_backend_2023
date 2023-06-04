package ac.kr.tukorea.capstone.post.repository;

import ac.kr.tukorea.capstone.post.entity.LikePost;
import ac.kr.tukorea.capstone.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikePostRepository extends JpaRepository<LikePost, Long> {
    List<LikePost> findByUser(User user);
    void deleteByPost_IdAndUser_Username(long postId, String username);
}
