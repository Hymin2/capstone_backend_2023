package ac.kr.tukorea.capstone.product.dto;

import ac.kr.tukorea.capstone.product.vo.ProductDetailVo;
import ac.kr.tukorea.capstone.product.vo.ProductVo;
import ac.kr.tukorea.capstone.product.vo.UsedProductPriceVo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailsDto {
    private long id;
    private List<ProductDetailVo> productDetails;
    private List<ProductVo> recommendProducts;
}

