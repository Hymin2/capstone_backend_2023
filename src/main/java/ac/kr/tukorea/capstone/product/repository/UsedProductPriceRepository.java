package ac.kr.tukorea.capstone.product.repository;

import ac.kr.tukorea.capstone.product.entity.UsedProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface UsedProductPriceRepository extends JpaRepository<UsedProductPrice, Long> {
    List<UsedProductPrice> findByTimeBefore(Date date);
    List<UsedProductPrice> findByProduct_IdOrderByTime(long productId);

}
