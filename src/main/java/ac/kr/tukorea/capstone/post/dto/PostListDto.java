package ac.kr.tukorea.capstone.post.dto;

import ac.kr.tukorea.capstone.post.vo.PostVo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostListDto {
    List<PostVo> Posts;
}
