package ac.kr.tukorea.capstone.user.repository;

import ac.kr.tukorea.capstone.user.entity.Follow;
import ac.kr.tukorea.capstone.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findByFollowingUser(User user);
    int countByFollowedUser(User user);
    int countByFollowingUser(User user);

    void deleteByFollowingUser_UsernameAndFollowedUser_Username(String followingUsername, String followerUsername);
}
