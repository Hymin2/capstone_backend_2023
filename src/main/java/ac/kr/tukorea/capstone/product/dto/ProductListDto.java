package ac.kr.tukorea.capstone.product.dto;


import ac.kr.tukorea.capstone.product.vo.ProductVo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductListDto {
    private long category_id;
    private String category_name;
    private List<ProductVo> productList;
}
