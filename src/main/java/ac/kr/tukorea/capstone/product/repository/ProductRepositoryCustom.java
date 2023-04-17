package ac.kr.tukorea.capstone.product.repository;

import ac.kr.tukorea.capstone.config.util.ProductFilter;
import ac.kr.tukorea.capstone.product.dto.ProductDetailsDto;
import ac.kr.tukorea.capstone.product.dto.ProductDto;
import ac.kr.tukorea.capstone.product.entity.Category;
import ac.kr.tukorea.capstone.product.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface ProductRepositoryCustom {
    List<ProductDto> findByCategoryAndFilter(Category category, List<ProductFilter> productFilters, String name);
    ProductDetailsDto findDetailsByProduct(Product product);
}
