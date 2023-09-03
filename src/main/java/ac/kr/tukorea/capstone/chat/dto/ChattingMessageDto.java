package ac.kr.tukorea.capstone.chat.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChattingMessageDto {
    private long roomId;
    private long postId;
    private String nickname;
    private String userType;
    private String message;
    private String messageType;
    private String time;
}
