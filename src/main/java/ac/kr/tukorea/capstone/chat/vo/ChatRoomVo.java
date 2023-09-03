package ac.kr.tukorea.capstone.chat.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChatRoomVo {
    private long roomId;
    private long postId;
    private String opponentUsername;
    private String opponentNickname;
    private String opponentUserImage;
    private String recentMessage;
    private String recentMessageTime;
}
