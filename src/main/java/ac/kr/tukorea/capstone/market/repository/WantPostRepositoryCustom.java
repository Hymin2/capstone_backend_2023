package ac.kr.tukorea.capstone.market.repository;

import ac.kr.tukorea.capstone.market.entity.Post;
import ac.kr.tukorea.capstone.user.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WantPostRepositoryCustom {
    List<Post> getWantPostList(User user);
}
