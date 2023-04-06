package ac.kr.tukorea.capstone.product.dto;


import ac.kr.tukorea.capstone.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductListDto {
    private List<Product> productList;
    private boolean isLast;
}
