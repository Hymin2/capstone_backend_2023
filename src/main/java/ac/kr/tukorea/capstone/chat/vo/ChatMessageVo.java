package ac.kr.tukorea.capstone.chat.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageVo {
    private String messageType;
    private String message;
    private String sendUsername;
    private String time;
}
