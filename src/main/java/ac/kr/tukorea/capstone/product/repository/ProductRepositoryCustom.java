package ac.kr.tukorea.capstone.product.repository;

import ac.kr.tukorea.capstone.config.util.ProductFilter;
import ac.kr.tukorea.capstone.product.dto.ProductDetailsDto;
import ac.kr.tukorea.capstone.product.vo.ProductDetailVo;
import ac.kr.tukorea.capstone.product.vo.ProductVo;
import ac.kr.tukorea.capstone.product.entity.Category;
import ac.kr.tukorea.capstone.product.entity.Product;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.List;

public interface ProductRepositoryCustom {
    List<ProductVo> findByCategoryAndFilter(Category category, List<BooleanExpression> productFilters, String name);
    List<ProductDetailVo> findDetailsByProduct(Product product);
}
