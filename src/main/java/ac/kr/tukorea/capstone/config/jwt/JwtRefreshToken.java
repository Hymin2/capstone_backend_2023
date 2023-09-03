package ac.kr.tukorea.capstone.config.jwt;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;

@RequiredArgsConstructor
@Getter
@RedisHash(value = "refresh_token")
public class JwtRefreshToken {
    @Id
    private final String refreshToken;
    private final String username;
}
