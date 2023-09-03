package ac.kr.tukorea.capstone.config.handler;

import ac.kr.tukorea.capstone.config.jwt.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
@RequiredArgsConstructor
public class StompHandler extends ChannelInterceptorAdapter {
    private final JwtTokenService jwtTokenService;

    @Override
    public void postSend(Message message, MessageChannel messageChannel, boolean sent){
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String sessionId = accessor.getSessionId();
        accessor.getSubscriptionId();

        switch (accessor.getCommand()){
            case CONNECT:
                try {
                    String accessToken = String.valueOf(accessor.getNativeHeader("Authorization").get(0));
                    jwtTokenService.validateAccessToken(jwtTokenService.getJwtToken(accessToken));

                } catch (RuntimeException e){
                    
                }
                System.out.println(sessionId + " open");
                break;
            case SUBSCRIBE:
                System.out.println(sessionId + " sub");
                break;
            case SEND:

                break;
            case DISCONNECT:
                System.out.println(sessionId + " closed");
                break;
            default:
                break;
        }
    }
}
