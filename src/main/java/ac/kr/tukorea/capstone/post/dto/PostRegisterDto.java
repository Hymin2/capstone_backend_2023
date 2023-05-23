package ac.kr.tukorea.capstone.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostRegisterDto {
    private long productId;
    private String userName;
    private String postTitle;
    private String postContent;
    private int price;
}
