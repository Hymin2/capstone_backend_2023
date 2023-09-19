package ac.kr.tukorea.capstone.chat.controller;

import ac.kr.tukorea.capstone.chat.dto.ChattingMessageDto;
import ac.kr.tukorea.capstone.chat.entity.ChatRoomParticipants;
import ac.kr.tukorea.capstone.chat.service.ChatService;
import ac.kr.tukorea.capstone.chat.service.RoomService;
import ac.kr.tukorea.capstone.config.util.MessageForm;
import ac.kr.tukorea.capstone.fcm.entity.FcmToken;
import ac.kr.tukorea.capstone.fcm.service.FcmService;
import ac.kr.tukorea.capstone.user.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessageSendingOperations sendingOperations;
    private final ChatService chatService;
    private final RoomService roomService;
    private final FcmService fcmService;
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
    private MessageForm messageForm = new MessageForm();

    @MessageMapping(value = "/chat/message")
    @Transactional
    public void message(ChattingMessageDto message) throws IOException {
        log.info("message : {} send by user : {} to room number : {}, userType : {}", message.getMessage(), message.getNickname(), message.getRoomId(), message.getUserType());

        ChatRoomParticipants chatRoomParticipants = roomService.getChatRoomParticipants(message.getRoomId()).orElse(null);
        System.out.println(chatRoomParticipants.getActualNumOfParticipants());

        boolean isCompleted = false;

        if (message.getMessageType().equals("REQUEST")) {
            isCompleted = chatService.dealComplete(message.getRoomId(), message.getPostId(), message.getUserType());
        }

        if (chatRoomParticipants.getActualNumOfParticipants() == 1) {
            List<String> usernames = chatRoomParticipants.getParticipants();

            for (String str : usernames) {
                if (!str.equals(message.getUsername())) {
                    FcmToken fcmToken = fcmService.getFcmToken(str).orElse(null);

                    if (fcmToken != null) {
                        try {
                            String fcmMessage = fcmService.makeMessage(fcmToken.getToken(),
                                    message.getRoomId(),
                                    message.getPostId(),
                                    message.getUsername(),
                                    message.getNickname(),
                                    null,
                                    message.getUserType(),
                                    message.getMessage(),
                                    message.getMessageType(),
                                    message.getTime());

                            fcmService.sendMessageTo(fcmMessage);

                            if(isCompleted){
                                message.setMessage("거래가 완료 됐습니다.");
                                message.setMessageType("COMPLETE");

                                String fcmCompleteMessage = fcmService.makeMessage(fcmToken.getToken(),
                                        message.getRoomId(),
                                        message.getPostId(),
                                        message.getUsername(),
                                        message.getNickname(),
                                        null,
                                        message.getUserType(),
                                        message.getMessage(),
                                        message.getMessageType(),
                                        message.getTime());

                                fcmService.sendMessageTo(fcmCompleteMessage);
                            }

                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        } else {
            if (message.getMessageType().equals("REQUEST")) {
                sendingOperations.convertAndSend("/sub/room/" + message.getRoomId(), message);

                if (isCompleted) {
                    message.setMessage("거래가 완료 됐습니다.");
                    message.setMessageType("COMPLETE");

                    sendingOperations.convertAndSend("/sub/room/" + message.getRoomId(), message);
                }
            } else sendingOperations.convertAndSend("/sub/room/" + message.getRoomId(), message);
        }
    }
}
