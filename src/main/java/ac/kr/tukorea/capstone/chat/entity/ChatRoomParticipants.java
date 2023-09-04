package ac.kr.tukorea.capstone.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChatRoomParticipants implements Serializable {
    private int actualNumOfParticipants;
    private List<String> participants;
}
