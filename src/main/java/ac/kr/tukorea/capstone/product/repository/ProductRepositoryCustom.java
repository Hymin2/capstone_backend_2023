package ac.kr.tukorea.capstone.product.repository;

import ac.kr.tukorea.capstone.product.vo.ProductDetailVo;
import ac.kr.tukorea.capstone.product.vo.ProductVo;
import ac.kr.tukorea.capstone.product.entity.Category;
import ac.kr.tukorea.capstone.product.entity.Product;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepositoryCustom {
    List<ProductVo> getProductList(long categoryId, List<BooleanExpression> productFilters, String name);
    List<ProductDetailVo> getProductDetailList(Product product);
    List<ProductVo> getTopProductList(long categoryId);
}
