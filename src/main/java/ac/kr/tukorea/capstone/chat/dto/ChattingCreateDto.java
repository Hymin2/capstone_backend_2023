package ac.kr.tukorea.capstone.chat.dto;


import ac.kr.tukorea.capstone.post.entity.Post;
import ac.kr.tukorea.capstone.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChattingCreateDto {
    private long salePostId;
    private long sellerId;
    private long buyerId;
}
