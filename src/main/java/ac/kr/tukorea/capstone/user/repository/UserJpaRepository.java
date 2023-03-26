package ac.kr.tukorea.capstone.user.repository;

import ac.kr.tukorea.capstone.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {
    boolean existsByUserId(String user_id);
    boolean existsByNickname(String nickname);
    void deleteByUserId(String user_id);
}
