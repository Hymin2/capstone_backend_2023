package ac.kr.tukorea.capstone.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;

@AllArgsConstructor
@Getter
@Setter
public class ChatRoomSession {
    private String username;
    private String destination;
}
