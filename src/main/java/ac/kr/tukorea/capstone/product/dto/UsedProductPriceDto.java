package ac.kr.tukorea.capstone.product.dto;

import ac.kr.tukorea.capstone.product.vo.UsedProductPriceVo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsedProductPriceDto {
    long productId;
    List<UsedProductPriceVo> usedProductPrices;
}
