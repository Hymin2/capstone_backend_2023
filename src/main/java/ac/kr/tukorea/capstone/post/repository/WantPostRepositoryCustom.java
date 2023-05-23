package ac.kr.tukorea.capstone.post.repository;

import ac.kr.tukorea.capstone.post.entity.Post;
import ac.kr.tukorea.capstone.post.vo.PostVo;
import ac.kr.tukorea.capstone.user.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WantPostRepositoryCustom {
    List<PostVo> getWantPostList(User user);
}
