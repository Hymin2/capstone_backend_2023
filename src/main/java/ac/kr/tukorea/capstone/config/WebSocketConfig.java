package ac.kr.tukorea.capstone.config;

import ac.kr.tukorea.capstone.config.handler.StompHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer  {
    private final StompHandler stompHandler;
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //endpoint를 /stomp로 하고, allowedOrigins를 "*"로 하면 페이지에서
        //Get /info 404 Error가 발생한다. 그래서 아래와 같이 2개의 계층으로 분리하고
        //origins를 개발 도메인으로 변경하니 잘 동작하였다.
        //이유는 왜 그런지 아직 찾지 못함
        registry.addEndpoint("/stomp/chat")
                .setAllowedOriginPatterns("*");
        //핸드셰이크 경로
    }

    /*어플리케이션 내부에서 사용할 path를 지정할 수 있음*/
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 메세지 발행 요청 url -> 메세지 보낼 때
        registry.setApplicationDestinationPrefixes("/pub");
        // 메세지 구독 요청 url -> 메세지 받을 때
        registry.enableSimpleBroker("/sub");
        // /sub로 시작하는 "destination" 헤더를 가진 메세지를 브로커로 라우팅한다.
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration channelRegistration){
        channelRegistration.interceptors(stompHandler);
    }
}
