package ac.kr.tukorea.capstone.market.dto;

import ac.kr.tukorea.capstone.market.vo.PostVo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.AccessType;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostListDto {
    List<PostVo> Posts;
}
