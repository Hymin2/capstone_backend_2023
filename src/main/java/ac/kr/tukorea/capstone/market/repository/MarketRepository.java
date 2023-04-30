package ac.kr.tukorea.capstone.market.repository;

import ac.kr.tukorea.capstone.market.entity.Market;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketRepository extends JpaRepository<Market, Long> {
}
