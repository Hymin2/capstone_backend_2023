package ac.kr.tukorea.capstone.fcm.repository;

import ac.kr.tukorea.capstone.fcm.entity.FcmToken;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class FcmTokenRedisRepository {
    private RedisTemplate redisTemplate;
    private final String hashKey = "fcm_token";

    public void save(String username, String fcmToken){
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        hashOperations.put(hashKey, username, fcmToken);
    }

    public Optional<FcmToken> findByUsername(String username){
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        String fcmToken = hashOperations.get(hashKey, username);

        if(fcmToken == null) return Optional.empty();

        return Optional.of(new FcmToken(username, fcmToken));
    }

    public void deleteByUsername(String username){
        redisTemplate.opsForHash().delete(hashKey, username);
    }
}
