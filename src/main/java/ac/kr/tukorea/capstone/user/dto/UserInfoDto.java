package ac.kr.tukorea.capstone.user.dto;

import ac.kr.tukorea.capstone.post.vo.PostVo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {
    private long userId;
    private String username;
    private String image;
    private int soldOut;
    private int onSale;
    private int followNum;
    private List<PostVo> posts;
}
