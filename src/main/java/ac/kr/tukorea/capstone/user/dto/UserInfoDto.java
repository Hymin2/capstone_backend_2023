package ac.kr.tukorea.capstone.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {
    private long userId;
    private String username;
    private String nickname;
    private String image;
    private int soldOut;
    private int onSale;
    private int followNum;
    private int followingNum;
    private boolean isFollow;

}
