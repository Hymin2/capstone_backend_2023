package ac.kr.tukorea.capstone.product.repository;

import ac.kr.tukorea.capstone.product.entity.Product;
import ac.kr.tukorea.capstone.product.vo.ProductDetailVo;
import ac.kr.tukorea.capstone.product.vo.ProductVo;
import ac.kr.tukorea.capstone.product.vo.UsedProductPriceVo;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ProductRepositoryCustom {
    List<ProductVo> getProductList(long categoryId, Map<String, List<BooleanExpression>> productFilters, String name);
    List<ProductDetailVo> getProductDetailList(long productId);
    List<ProductVo> getTopProductList(long categoryId);
    List<UsedProductPriceVo> getUsedProductPriceList(long productId);
}
