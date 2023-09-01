package ac.kr.tukorea.capstone.fcm.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;

@RequiredArgsConstructor
@Getter
@RedisHash("fcm")
public class FcmToken {
    @Id
    private String username;
    private String token;
}
