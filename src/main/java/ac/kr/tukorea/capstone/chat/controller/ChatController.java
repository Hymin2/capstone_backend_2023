package ac.kr.tukorea.capstone.chat.controller;

import ac.kr.tukorea.capstone.chat.dto.ChatRoomCreateDto;
import ac.kr.tukorea.capstone.chat.dto.ChattingMessageDto;
import ac.kr.tukorea.capstone.chat.entity.ChattingRoom;
import ac.kr.tukorea.capstone.chat.service.ChatService;
import ac.kr.tukorea.capstone.config.util.MessageForm;
import ac.kr.tukorea.capstone.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessageSendingOperations sendingOperations;
    private final ChatService chatService;
    private final UserService userService;
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
    private MessageForm messageForm = new MessageForm();

    @MessageMapping(value = "/chat/message")
    @Transactional
    public void message(ChattingMessageDto message) throws IOException {
        log.info("message : {} send by user : {} to room number : {}", message.getMessage(), message.getNickname(), message.getRoomId());

        if(message.getMessageType().equals("REQUEST")){
            sendingOperations.convertAndSend("/sub/room/" + message.getRoomId(), message);

            if(chatService.dealComplete(message.getRoomId(), message.getPostId(), message.getUserType())){
                message.setMessage("거래가 완료 됐습니다.");
                message.setMessageType("COMPLETE");

                sendingOperations.convertAndSend("/sub/room/" + message.getRoomId(), message);
            }
        } else sendingOperations.convertAndSend("/sub/room/" + message.getRoomId(), message);
    }
}
