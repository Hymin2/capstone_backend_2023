package ac.kr.tukorea.capstone.config.jwt;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
@AllArgsConstructor
public class JwtRefreshTokenRepository {
    private RedisTemplate redisTemplate;
    private final String hashKey = "refresh_token";

    public void save(JwtRefreshToken jwtRefreshToken){
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();

        Map<String, String> map = new HashMap<>();
        map.put(jwtRefreshToken.getRefreshToken(), jwtRefreshToken.getUsername());

        hashOperations.putAll(hashKey, map);
    }

    public Optional<JwtRefreshToken> findByRefreshToken(String refreshToken){
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        String username = hashOperations.get(hashKey, refreshToken);

        if(Objects.isNull(username))
            return Optional.empty();

        return Optional.of(new JwtRefreshToken(refreshToken, username));

    }

    public void deleteByRefreshToken(String refreshToken){
        redisTemplate.opsForHash().delete(hashKey, refreshToken);
    }
}
