package ac.kr.tukorea.capstone.product.dto;

import ac.kr.tukorea.capstone.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private long id;
    private String productName;
    private String modelName;
    private String companyName;
    private String path;
}
