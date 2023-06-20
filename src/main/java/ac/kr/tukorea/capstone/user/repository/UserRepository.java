package ac.kr.tukorea.capstone.user.repository;

import ac.kr.tukorea.capstone.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    boolean existsByNickname(String nickname);
    void deleteByUsername(String username);
    Optional<User> findByUsername(String username);

}
