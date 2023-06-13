package ac.kr.tukorea.capstone.user.repository;

import ac.kr.tukorea.capstone.user.entity.User;
import ac.kr.tukorea.capstone.user.vo.UserVo;

import java.util.List;

public interface FollowRepositoryCustom {
    List<UserVo> getFollowingList(User user);
    List<UserVo> getFollowerList(User user);
}
