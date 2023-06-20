package ac.kr.tukorea.capstone.chat.controller;

import ac.kr.tukorea.capstone.chat.dto.ChattingCreateDto;
import ac.kr.tukorea.capstone.chat.dto.ChattingMessageDto;
import ac.kr.tukorea.capstone.chat.service.ChatService;
import ac.kr.tukorea.capstone.config.util.MessageForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.WebSocketSession;

import java.net.http.WebSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/chat")
public class ChatController {

    private List<Map> rooms = new ArrayList<Map>();
    private Map<String, WebSocketSession> user = new HashMap<>();
    private final ChatService chatService;

    private MessageForm messageForm = new MessageForm();

    @PostMapping(value = "/create")
    public ResponseEntity<MessageForm> createRoom(@RequestBody ChattingCreateDto chattingCreateDto) {
        try{
            chatService.createRoom(chattingCreateDto);
            //user.put()
            //WebSocket session = new WebSocket()

        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(messageForm);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(messageForm);
    }
    @PostMapping(value = "/chat")
    public ResponseEntity<MessageForm> createContent(@RequestBody ChattingMessageDto chattingMessageDto){
        try{
            chatService.createContent(chattingMessageDto);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(messageForm);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(messageForm);
    }


    //@GetMapping
    /*public List<ChattingRoom> findAllRoom() {
        return chatService.findAllRoom();
    }*/
}
