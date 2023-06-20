package ac.kr.tukorea.capstone.config.handler;

import ac.kr.tukorea.capstone.chat.dto.ChattingMessageDto;
import ac.kr.tukorea.capstone.chat.service.ChatService;
import ac.kr.tukorea.capstone.chat.vo.ChatRoomManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebSocketHandler extends TextWebSocketHandler {
    private ChatRoomManager chatRoomManager;
    private final Map<String, WebSocketSession> sessions = new HashMap<>();
    private final List<Map> rooms = new ArrayList<Map>();
    private final ObjectMapper objectMapper;
    private final ChatService chatService;
    @Override
        public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String seller = session.getAttributes().get("seller").toString(); // 판매자의 id
        String buyer = session.getAttributes().get("buyer").toString(); // 구매자의 id

        String roomId = String.valueOf(chatRoomManager.roomSize()); // 현재 채팅방 크기를 구하고 roomId에 할당

        chatRoomManager.createChatRoom(roomId); // 새로운 채팅방 생성
        chatRoomManager.getChatRoom(roomId).addParticipant(seller, session); // 채팅방에 판매자 클라이언트 추가
        //구매자 세션 찾기 칠요
        chatRoomManager.getChatRoom(roomId).addParticipant(buyer, session); // 채팅방에 구매자 클라이언트 추가 <- 수정해야함
        /*JPA 채팅방 생성*/
        //chatService.createRoom()
        log.info(buyer + "가 " + seller + "와의 채팅방 개설");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        chatRoomManager.removeChatRoom(session.getAttributes().get("roomId").toString());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("{}", payload);

        JSONObject jsonObject = new JSONObject(payload);
        String text = jsonObject.getString("payload");
        JSONObject info = jsonObject.getJSONObject("info");

        //String target = info.getString("target"); // 메시지 받을 사람 id

        ChattingMessageDto chattingMessageDto = objectMapper.readValue(payload, ChattingMessageDto.class); // 채팅 매핑

        chatRoomManager.getChatRoom(chattingMessageDto.getRoomId().toString())
                .broadcastMessage(chattingMessageDto.getContent().toString()); // 채팅방에 있는 모두에게 메시지 전송

        /* JPA에 채팅내역 저장해야함*/
        chatService.createContent(chattingMessageDto);
    }
}
