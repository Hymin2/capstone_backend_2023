package ac.kr.tukorea.capstone.product.repository;

import ac.kr.tukorea.capstone.product.entity.Product;
import ac.kr.tukorea.capstone.product.entity.UsedProductPrice;
import ac.kr.tukorea.capstone.product.vo.UsedProductPriceVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface UsedProductPriceRepository extends JpaRepository<UsedProductPrice, Long> {
    List<UsedProductPrice> findByTimeBefore(Date date);

}
