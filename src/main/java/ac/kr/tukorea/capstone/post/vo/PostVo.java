package ac.kr.tukorea.capstone.post.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostVo {
    private long id;
    private String username;
    private String userImage;
    private String postTitle;
    private String postContent;
    private String isOnSale;
    private LocalDateTime createdTime;
    private List<String> postImages;
}
