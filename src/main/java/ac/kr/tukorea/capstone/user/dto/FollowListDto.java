package ac.kr.tukorea.capstone.user.dto;

import ac.kr.tukorea.capstone.user.vo.UserVo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FollowListDto {
    private long id;
    private String username;
    private List<UserVo> follows;

}
