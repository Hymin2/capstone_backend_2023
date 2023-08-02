package ac.kr.tukorea.capstone.product.repository;

import ac.kr.tukorea.capstone.product.vo.ProductDetailVo;
import ac.kr.tukorea.capstone.product.vo.ProductVo;
import ac.kr.tukorea.capstone.product.vo.RecommendProductVo;
import ac.kr.tukorea.capstone.product.vo.UsedProductPriceVo;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ProductRepositoryCustom {
    List<ProductVo> getFilterProductList(long categoryId, Map<String, List<BooleanExpression>> productFilters, String name);
    List<ProductVo> getTopProductList(long categoryId);
    List<ProductVo> getProductListByProductId(List<Long> productIdList);
    List<ProductDetailVo> getProductDetailList(long productId);
    List<UsedProductPriceVo> getUsedProductPriceList(long productId);
    List<RecommendProductVo> getRecommendProductList(long productId);
}
