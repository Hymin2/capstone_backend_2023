package ac.kr.tukorea.capstone.chat.controller;

import ac.kr.tukorea.capstone.chat.dto.ChattingCreateDto;
import ac.kr.tukorea.capstone.chat.dto.ChattingMessageDto;
import ac.kr.tukorea.capstone.chat.entity.ChattingContent;
import ac.kr.tukorea.capstone.chat.entity.ChattingRoom;
import ac.kr.tukorea.capstone.chat.service.ChatService;
import ac.kr.tukorea.capstone.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class StompChatController {
    private final SimpMessageSendingOperations sendingOperations;
    private final ChatService chatService;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate template; //특정 Broker로 메세지를 전달

    //Client가 SEND할 수 있는 경로
    //stompConfig에서 설정한 applicationDestinationPrefixes와 @MessageMapping 경로가 병합됨
    //"/pub/chat/enter"
    @MessageMapping(value = "/chat/create")
    public void enter(ChattingCreateDto chattingCreateDto){

        ChattingRoom chattingRoom = chatService.createRoom(chattingCreateDto);

        String buyer = chattingRoom.getBuyer().getUsername() + "님이 채팅방에 참여하였습니다.";
        String seller = chattingRoom.getSeller().getUsername() + "님이 채팅방에 참여하였습니다.";

        /*ChattingMessageDto message = new ChattingMessageDto();
        message.setContent(chattingCreateDto.getBuyer() + "님이 채팅방에 참여하였습니다.");
        message.setUserId(chattingRoom.getBuyer().getBuyer().get((int) chattingCreateDto.getBuyer().getId()));
        User userId = userRepository.findById(chattingCreateDto.getBuyer().getId());
        message.setUserId(chattingRoom.getBuyer());*/

        template.convertAndSend("/sub/chat/" + chattingRoom.getId(), buyer);
        template.convertAndSend("/sub/chat/" + chattingRoom.getId(), seller);

    }

    @MessageMapping(value = "/chat/message")
    public void message(ChattingMessageDto message){

        ChattingContent chattingContent = chatService.createContent(message);

        template.convertAndSend("/sub/chat/" + message.getRoomId(), chattingContent.getContent());
    }
}
