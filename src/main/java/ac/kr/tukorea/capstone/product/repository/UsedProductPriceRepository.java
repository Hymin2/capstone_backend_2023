package ac.kr.tukorea.capstone.product.repository;

import ac.kr.tukorea.capstone.product.entity.Product;
import ac.kr.tukorea.capstone.product.entity.UsedProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsedProductPriceRepository extends JpaRepository<UsedProductPrice, Long> {
   List<UsedProductPrice> findByProduct(Product product);
}
