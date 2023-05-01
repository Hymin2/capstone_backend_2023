package ac.kr.tukorea.capstone.market.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MarketRegisterDto {
    private String username;
    private String marketName;
}
