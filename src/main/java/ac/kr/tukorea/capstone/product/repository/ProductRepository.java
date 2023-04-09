package ac.kr.tukorea.capstone.product.repository;

import ac.kr.tukorea.capstone.product.entity.Category;
import ac.kr.tukorea.capstone.product.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom{
    Slice<Product> findByCategory(Category category, Pageable pageable);
}
