package ac.kr.tukorea.capstone.chat.repository;

import ac.kr.tukorea.capstone.chat.entity.ChatRoomParticipants;
import ac.kr.tukorea.capstone.chat.entity.ChatRoomSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ChatRoomParticipantsRepository {
    private final RedisTemplate redisTemplate;
    private final String hashKey = "room_participants";

    public void save(Long room, int number, List<String> usernames){
        HashOperations<String, Long, ChatRoomParticipants> hashOperations = redisTemplate.opsForHash();
        hashOperations.put(hashKey, room, new ChatRoomParticipants(number, usernames));
    }

    public void save(Long room, ChatRoomParticipants chatRoomParticipants){
        HashOperations<String, Long, ChatRoomParticipants> hashOperations = redisTemplate.opsForHash();
        hashOperations.put(hashKey, room, chatRoomParticipants);
    }

    public Optional<ChatRoomParticipants> findByRoom(Long room){
        HashOperations<String, Long, ChatRoomParticipants> hashOperations = redisTemplate.opsForHash();
        ChatRoomParticipants chatRoomParticipants = hashOperations.get(hashKey, room);

        if(chatRoomParticipants == null) return Optional.empty();

        return Optional.of(chatRoomParticipants);
    }

    public void update(Long room, ChatRoomParticipants chatRoomParticipants){
        save(room, chatRoomParticipants);
    }
}
