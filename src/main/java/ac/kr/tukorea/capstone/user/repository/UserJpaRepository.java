package ac.kr.tukorea.capstone.user.repository;

import ac.kr.tukorea.capstone.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {
    boolean existsByUserName(String username);
    boolean existsByNickname(String nickname);
    void deleteByUserName(String username);
    Optional<User> findByUserName(String username);
}
