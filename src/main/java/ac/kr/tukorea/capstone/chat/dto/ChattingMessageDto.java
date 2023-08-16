package ac.kr.tukorea.capstone.chat.dto;


import ac.kr.tukorea.capstone.chat.entity.ChattingContent;
import ac.kr.tukorea.capstone.chat.entity.ChattingRoom;
import lombok.*;
import org.aspectj.bridge.Message;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChattingMessageDto {
    private long roomId;
    private long userId;
    private long postId;
    private String content;
    private String userType;
    private String messageType;
}
