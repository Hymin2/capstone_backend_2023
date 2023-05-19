package ac.kr.tukorea.capstone.market.repository;

import ac.kr.tukorea.capstone.market.entity.FollowMarket;
import ac.kr.tukorea.capstone.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowMarketRepository extends JpaRepository<FollowMarket, Long> {
    List<FollowMarket> findByUser(User user);
}
