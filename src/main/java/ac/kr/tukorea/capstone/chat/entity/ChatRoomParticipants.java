package ac.kr.tukorea.capstone.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChatRoomParticipants {
    private int actualNumOfParticipants;
    private List<String> participants;
}
