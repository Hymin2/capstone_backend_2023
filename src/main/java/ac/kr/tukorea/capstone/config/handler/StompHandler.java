package ac.kr.tukorea.capstone.config.handler;

import ac.kr.tukorea.capstone.chat.entity.ChatRoomParticipants;
import ac.kr.tukorea.capstone.chat.entity.ChatRoomSession;
import ac.kr.tukorea.capstone.chat.service.RoomService;
import ac.kr.tukorea.capstone.config.jwt.JwtTokenService;
import ac.kr.tukorea.capstone.fcm.service.FcmService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StompHandler extends ChannelInterceptorAdapter {
    private final JwtTokenService jwtTokenService;
    private final RoomService roomService;
    private final FcmService fcmService;

    @Override
    public void postSend(Message message, MessageChannel messageChannel, boolean sent){
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        ChatRoomParticipants chatRoomParticipants;
        JSONObject jsonObject;

        String sessionId = accessor.getSessionId();
        String username;
        String dest;

        int actualNum;
        long room;

        switch (accessor.getCommand()){
            case CONNECT:
                try {
                    String accessToken = String.valueOf(accessor.getNativeHeader("Authorization").get(0));
                    jwtTokenService.validateAccessToken(jwtTokenService.getJwtToken(accessToken));

                    username = jwtTokenService.getUsername(accessToken);

                    roomService.saveChatRoomSession(sessionId, username, null);
                } catch (RuntimeException e){
                    // 인증 오류
                }
                break;
            case SUBSCRIBE:
                jsonObject = new JSONObject(message.getHeaders());
                dest = (String) jsonObject.get("simpDestination");

                ChatRoomSession chatRoomSession = roomService.getChatRoomSession(sessionId).orElse(null);
                chatRoomSession.setDestination(dest);

                roomService.updateChatRoomSession(sessionId, chatRoomSession);

                room = getRoomId(dest));
                chatRoomParticipants = roomService.getChatRoomParticipants(room).orElse(null);

                actualNum = chatRoomParticipants.getActualNumOfParticipants();

                chatRoomParticipants.setActualNumOfParticipants(actualNum + 1);

                roomService.updateChatRoomParticipants(room, chatRoomParticipants);

                break;
            case SEND:
                room = jsonObject.getLong("roomId");

                chatRoomParticipants = roomService.getChatRoomParticipants(room).orElse(null);

                if(chatRoomParticipants.getActualNumOfParticipants() == 1) {
                    jsonObject = new JSONObject(message.getPayload());

                    String nickname = jsonObject.getString("nickname");
                    String talkMessage = jsonObject.getString("message");
                    String messageType = jsonObject.getString("messageType");
                    String datetime = jsonObject.getString("time");
                    long postId = jsonObject.getLong("postId");

                    username = jsonObject.getString("username");


                }
                break;
            case DISCONNECT:
                System.out.println("DISCONNECT");

                dest = roomService.getChatRoomSession(sessionId).orElse(null).getDestination();
                room = getRoomId(dest);

                chatRoomParticipants = roomService.getChatRoomParticipants(room).orElse(null);

                actualNum = chatRoomParticipants.getActualNumOfParticipants();
                chatRoomParticipants.setActualNumOfParticipants(actualNum - 1);

                roomService.updateChatRoomParticipants(room, chatRoomParticipants);
                roomService.deleteChatRoomSession(sessionId);

                break;
            default:
                break;
        }
    }

    public long getRoomId(String dest){
        return Long.parseLong(dest.split("/")[2]);
    }
}
