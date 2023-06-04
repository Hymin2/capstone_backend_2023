package ac.kr.tukorea.capstone.post.vo;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostVo {
    private long id;
    private String username;
    private String nickname;
    private String userImage;
    private String productName;
    private String postTitle;
    private String postContent;
    private String isOnSale;
    private int price;
    private LocalDateTime createdTime;
    private List<String> postImages;
    private boolean isLike;

    public PostVo(long id, String username, String nickname, String userImage, String productName, String postTitle, String postContent, String isOnSale, int price, LocalDateTime createdTime, List<String> postImages, Integer isLike){
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.userImage = userImage;
        this.productName = productName;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.isOnSale = isOnSale;
        this.price = price;
        this.createdTime = createdTime;
        this.postImages = postImages;
        this.isLike = isLike != null;
    }
}
