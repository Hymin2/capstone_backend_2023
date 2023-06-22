package ac.kr.tukorea.capstone.chat.dto;


import ac.kr.tukorea.capstone.chat.entity.ChattingContent;
import ac.kr.tukorea.capstone.chat.entity.ChattingRoom;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChattingMessageDto {
    @NotEmpty
    private ChattingRoom roomId;
    @NotEmpty
    private ChattingRoom userId;
    @NotEmpty
    private String content;

    /*public ChattingMessageDto(int roomId, int userId, String content){
        this.roomId.setId(roomId);
        this.userId.getChattingRoomForUserIds().get(userId);
        this.content = content;
    }*/

}
