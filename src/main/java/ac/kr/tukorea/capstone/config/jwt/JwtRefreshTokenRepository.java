package ac.kr.tukorea.capstone.config.jwt;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
@AllArgsConstructor
public class JwtRefreshTokenRepository {
    private RedisTemplate redisTemplate;

    public void save(JwtRefreshToken jwtRefreshToken){
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(jwtRefreshToken.getRefreshToken(), jwtRefreshToken.getUsername());
        redisTemplate.expire(jwtRefreshToken.getRefreshToken(), 30L, TimeUnit.DAYS);
    }

    public Optional<JwtRefreshToken> findByRefreshToken(String refreshToken){
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String username = valueOperations.get(refreshToken);

        if(Objects.isNull(username))
            return Optional.empty();

        return Optional.of(new JwtRefreshToken(refreshToken, username));

    }

    public void deleteByRefreshToken(String refreshToken){
        redisTemplate.delete(refreshToken);
    }
}
