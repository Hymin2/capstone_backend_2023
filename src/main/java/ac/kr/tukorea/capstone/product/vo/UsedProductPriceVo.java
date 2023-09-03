package ac.kr.tukorea.capstone.product.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsedProductPriceVo {
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date time;
    private int price;

    public UsedProductPriceVo(Date time){
        this.time = time;
        this.price = 0;
    }
}
