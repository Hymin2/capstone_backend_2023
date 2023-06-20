package ac.kr.tukorea.capstone.chat.dto;


import ac.kr.tukorea.capstone.chat.entity.ChattingRoom;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class ChattingMessageDto {
    @NotEmpty
    private ChattingRoom roomId;
    @NotEmpty
    private ChattingRoom userId;
    @NotEmpty
    private String content;
}
