package ac.kr.tukorea.capstone.user.repository;

import ac.kr.tukorea.capstone.user.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
