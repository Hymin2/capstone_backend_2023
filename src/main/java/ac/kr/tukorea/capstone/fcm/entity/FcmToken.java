package ac.kr.tukorea.capstone.fcm.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@RedisHash("fcm_token")
public class FcmToken {
    @Id
    private String username;
    private String token;
}
