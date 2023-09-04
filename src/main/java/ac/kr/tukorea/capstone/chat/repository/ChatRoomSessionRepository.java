package ac.kr.tukorea.capstone.chat.repository;

import ac.kr.tukorea.capstone.chat.entity.ChatRoomSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ChatRoomSessionRepository {
    private final RedisTemplate redisTemplate;
    private final String hashKey = "room_session";

    public void save(String sessionId, String username, String destination){
        HashOperations<String, String, ChatRoomSession> hashOperations = redisTemplate.opsForHash();
        hashOperations.put(hashKey, sessionId, new ChatRoomSession(username, destination));
    }

    public void save(String sessionId, ChatRoomSession chatRoomSession){
        HashOperations<String, String, ChatRoomSession> hashOperations = redisTemplate.opsForHash();
        hashOperations.put(hashKey, sessionId, chatRoomSession);
    }

    public Optional<ChatRoomSession> findBySessionId(String sessionId){
        HashOperations<String, String, ChatRoomSession> hashOperations = redisTemplate.opsForHash();
        ChatRoomSession chatRoomSession = hashOperations.get(hashKey, sessionId);

        if(chatRoomSession == null) return Optional.empty();

        return Optional.of(chatRoomSession);
    }

    public void update(String sessionId, ChatRoomSession chatRoomSession){
        save(sessionId, chatRoomSession);
    }

    public void delete(String sessionId){
        HashOperations<String, String, ChatRoomSession> hashOperations = redisTemplate.opsForHash();
        hashOperations.delete(hashKey, sessionId);
    }
}
