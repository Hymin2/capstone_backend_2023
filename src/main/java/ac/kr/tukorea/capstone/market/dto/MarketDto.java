package ac.kr.tukorea.capstone.market.dto;

import ac.kr.tukorea.capstone.market.vo.PostVo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MarketDto {
    private long id;
    private String marketName;
    private String marketImg;
    private int soldOut;
    private int onSales;
    private List<PostVo> posts;
}
