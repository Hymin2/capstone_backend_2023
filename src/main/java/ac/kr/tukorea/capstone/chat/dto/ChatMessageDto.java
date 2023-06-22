package ac.kr.tukorea.capstone.chat.dto;

import ac.kr.tukorea.capstone.chat.entity.ChattingRoom;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDto {
    @NotEmpty
    private int roomId;
    @NotEmpty
    private int userId;
    @NotEmpty
    private String content;

}
