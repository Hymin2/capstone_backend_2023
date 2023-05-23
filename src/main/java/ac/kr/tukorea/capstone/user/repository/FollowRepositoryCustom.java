package ac.kr.tukorea.capstone.user.repository;

import ac.kr.tukorea.capstone.user.entity.User;
import ac.kr.tukorea.capstone.user.vo.FollowVo;

import java.util.List;

public interface FollowRepositoryCustom {
    List<FollowVo> getFollowList(User user);
}
