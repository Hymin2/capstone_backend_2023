package ac.kr.tukorea.capstone.product.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecommendProductVo {
    private long productId;
    private int detailNum;
    private int transactionNum;
    private int averagePrice;
    private int score;

    public RecommendProductVo(long productId, int detailNum, int transactionNum, int averagePrice){
        this.productId = productId;
        this.detailNum = detailNum;
        this.transactionNum = transactionNum;
        this.averagePrice = averagePrice;
        this.score = 0;
    }
}
