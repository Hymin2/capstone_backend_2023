package ac.kr.tukorea.capstone.chat.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;

@RequiredArgsConstructor
@Getter
@RedisHash("chat_session")
public class ChattingRoomSession {
    @Id
    Long roomId;
    int participantNum;

}
