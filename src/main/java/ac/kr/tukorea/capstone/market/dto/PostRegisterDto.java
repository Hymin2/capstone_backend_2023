package ac.kr.tukorea.capstone.market.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostRegisterDto {
    private long productId;
    private String marketName;
    private String postTitle;
    private String postContent;
    private int price;
}
