package ac.kr.tukorea.capstone.post.repository;

import ac.kr.tukorea.capstone.post.entity.WantPost;
import ac.kr.tukorea.capstone.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WantPostRepository extends JpaRepository<WantPost, Long> {
    List<WantPost> findByUser(User user);
}
