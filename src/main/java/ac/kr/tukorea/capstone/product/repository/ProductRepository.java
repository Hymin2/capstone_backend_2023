package ac.kr.tukorea.capstone.product.repository;

import ac.kr.tukorea.capstone.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
