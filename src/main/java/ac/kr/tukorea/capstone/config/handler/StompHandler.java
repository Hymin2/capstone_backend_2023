package ac.kr.tukorea.capstone.config.handler;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.stereotype.Component;

@Component
public class StompHandler extends ChannelInterceptorAdapter {
    @Override
    public void postSend(Message message, MessageChannel messageChannel, boolean sent){
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String sessionId = accessor.getSessionId();

        switch (accessor.getCommand()){
            case CONNECT:

                break;
            case DISCONNECT:

                break;
            default:
                break;
        }
    }
}
