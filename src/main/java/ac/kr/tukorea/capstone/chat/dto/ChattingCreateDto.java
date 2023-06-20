package ac.kr.tukorea.capstone.chat.dto;


import ac.kr.tukorea.capstone.post.entity.Post;
import ac.kr.tukorea.capstone.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChattingCreateDto {

    @NotEmpty
    private Post sale_post_id;
    @NotEmpty
    private User seller;
    @NotEmpty
    private User buyer;
}
