package ac.kr.tukorea.capstone.chat.vo;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ChatRoom {
    private Map<String, WebSocketSession> sessions = new HashMap<>();

    public void addParticipant(String userId, WebSocketSession session) {
        sessions.put(userId, session);
    }

    public void removeParticipant(String userId) {
        sessions.remove(userId);
    }

    // 채팅 메시지를 모든 참여자에게 전송
    public void broadcastMessage(String message) throws IOException {
        for (WebSocketSession session : sessions.values()) {
            session.sendMessage(new TextMessage(message));
        }
    }
}

