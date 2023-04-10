package ac.kr.tukorea.capstone.product.repository;

import ac.kr.tukorea.capstone.product.dto.ProductDto;
import ac.kr.tukorea.capstone.product.entity.Category;
import ac.kr.tukorea.capstone.product.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface ProductRepositoryCustom {
    Slice<ProductDto> findByCategoryAndFilter(Category category, String[][] filters, Pageable pageable);
}
