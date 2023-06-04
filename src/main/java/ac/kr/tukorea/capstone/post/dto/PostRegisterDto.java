package ac.kr.tukorea.capstone.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostRegisterDto {
    private long productId;
    private String username;
    private String postTitle;
    private String postContent;
    private int price;
}
