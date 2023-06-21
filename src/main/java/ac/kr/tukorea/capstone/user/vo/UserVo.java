package ac.kr.tukorea.capstone.user.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserVo {
    private long id;
    private String username;
    private String nickname;
    private String image;
}
