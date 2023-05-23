package ac.kr.tukorea.capstone.post.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostUpdateDto {
    private String postTitle;
    private String postContent;
    private String isOnSale;
    private int price;

}
