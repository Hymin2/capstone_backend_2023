package ac.kr.tukorea.capstone.config.handler;

import ac.kr.tukorea.capstone.chat.entity.ChatRoomParticipants;
import ac.kr.tukorea.capstone.chat.entity.ChatRoomSession;
import ac.kr.tukorea.capstone.chat.service.RoomService;
import ac.kr.tukorea.capstone.config.Exception.AccessTokenExpiredException;
import ac.kr.tukorea.capstone.config.Exception.InvalidAccessTokenException;
import ac.kr.tukorea.capstone.config.jwt.JwtTokenService;
import ac.kr.tukorea.capstone.fcm.entity.FcmToken;
import ac.kr.tukorea.capstone.fcm.service.FcmService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

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

        try {
            switch (accessor.getCommand()) {
                case CONNECT:
                    try {
                        String accessToken = String.valueOf(accessor.getNativeHeader("Authorization").get(0));

                        accessToken = jwtTokenService.getJwtToken(accessToken);
                        jwtTokenService.validateAccessToken(accessToken);

                        username = jwtTokenService.getUsername(accessToken);

                        roomService.saveChatRoomSession(sessionId, username, null);
                    } catch (ExpiredJwtException e) {
                        System.out.println("유효 기간이 지난 Access Token");
                        throw new AccessTokenExpiredException();
                    } catch (JwtException e) {
                        throw new InvalidAccessTokenException();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case SUBSCRIBE:
                    jsonObject = new JSONObject(message.getHeaders());
                    dest = (String) jsonObject.get("simpDestination");

                    ChatRoomSession chatRoomSession = roomService.getChatRoomSession(sessionId).orElse(null);
                    chatRoomSession.setDestination(dest);

                    roomService.updateChatRoomSession(sessionId, chatRoomSession);

                    room = getRoomId(dest);
                    chatRoomParticipants = roomService.getChatRoomParticipants(room).orElse(null);

                    actualNum = chatRoomParticipants.getActualNumOfParticipants();

                    chatRoomParticipants.setActualNumOfParticipants(actualNum + 1);

                    roomService.updateChatRoomParticipants(room, chatRoomParticipants);
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
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    public long getRoomId(String dest){
        System.out.println(dest.split("/")[3]);
        return Long.parseLong(dest.split("/")[3]);
    }
}
