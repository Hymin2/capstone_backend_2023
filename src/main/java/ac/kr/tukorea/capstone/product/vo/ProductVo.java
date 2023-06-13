package ac.kr.tukorea.capstone.product.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductVo {
    private long id;
    private String productName;
    private String modelName;
    private String companyName;
    private int averagePrice;
    private int transactionNum;
    private List<String> images;
}
