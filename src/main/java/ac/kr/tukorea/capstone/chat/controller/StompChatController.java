package ac.kr.tukorea.capstone.chat.controller;

import ac.kr.tukorea.capstone.chat.dto.ChatMessageDto;
import ac.kr.tukorea.capstone.chat.dto.ChattingCreateDto;
import ac.kr.tukorea.capstone.chat.dto.ChattingMessageDto;
import ac.kr.tukorea.capstone.chat.entity.ChattingContent;
import ac.kr.tukorea.capstone.chat.entity.ChattingRoom;
import ac.kr.tukorea.capstone.chat.service.ChatService;
import ac.kr.tukorea.capstone.config.util.MessageForm;
import ac.kr.tukorea.capstone.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.io.DataInput;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class StompChatController {
    private final ObjectMapper objectMapper;
    private final SimpMessageSendingOperations sendingOperations;
    private final ChatService chatService;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate template; //특정 Broker로 메세지를 전달
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
    private MessageForm messageForm = new MessageForm();

    //Client가 SEND할 수 있는 경로
    //stompConfig에서 설정한 applicationDestinationPrefixes와 @MessageMapping 경로가 병합됨
    //"/pub/chat/create"
    @MessageMapping(value = "/chat/create")
    public ResponseEntity<MessageForm> enter(ChattingCreateDto chattingCreateDto){

        ChattingRoom chattingRoom = chatService.createRoom(chattingCreateDto);

        String buyer = chattingCreateDto.getBuyer().getUsername() + "님이 채팅방에 참여하였습니다.";
        String seller = chattingCreateDto.getSeller().getUsername() + "님이 채팅방에 참여하였습니다.";

        /*ChattingMessageDto message = new ChattingMessageDto();
        message.setContent(chattingCreateDto.getBuyer() + "님이 채팅방에 참여하였습니다.");
        message.setUserId(chattingRoom.getBuyer().getBuyer().get((int) chattingCreateDto.getBuyer().getId()));
        User userId = userRepository.findById(chattingCreateDto.getBuyer().getId());
        message.setUserId(chattingRoom.getBuyer());*/

        template.convertAndSend("/sub/chat/" + chattingCreateDto.getBuyer().getId(), buyer);
        template.convertAndSend("/sub/chat/" + chattingCreateDto.getSeller().getId(), seller);
        template.convertAndSend("/sub/chat/" + chattingCreateDto.getSeller().getId(), seller);
        autoSubscribe(chattingRoom.getId(),chattingCreateDto.getSeller().getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(messageForm);
    }

    @MessageMapping(value = "/chat/message")
    public void message(ChattingMessageDto message) throws IOException {
        // public void message(ChattingMessageDto message) -> public void message(ChatMessageDto message) 변경시 채팅은 됨
        log.info("message : {} send by user : {} to room number : {}", message.getContent(), message.getUserId(), message.getRoomId());

        //chatService.createContent(message);

        //template.convertAndSend("/sub/chat/" + message.getRoomId(), message);

        sendingOperations.convertAndSend("/sub/room/" + message.getRoomId(), message);
    }

    @MessageMapping(value = "/chat/auto_subscribe")
    public ResponseEntity<MessageForm> autoSubscribe(long roomId, long sellerId){
        //long message = roomId;//구독해야할 roomId 들어가야함
        sendingOperations.convertAndSend("/sub/auto/" + sellerId, roomId);
        return ResponseEntity.status(HttpStatus.CREATED).body(messageForm);
    }
}
