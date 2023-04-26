package ac.kr.tukorea.capstone.product.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductVo {
    private long id;
    private String productName;
    private String modelName;
    private String companyName;
    private String path;
    private int averagePrice;

    public void setAveragePrice(int averagePrice){
        this.averagePrice = averagePrice;
    }
}
