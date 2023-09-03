package ac.kr.tukorea.capstone.chat.controller;

import ac.kr.tukorea.capstone.chat.dto.ChatRoomCreateDto;
import ac.kr.tukorea.capstone.chat.service.RoomService;
import ac.kr.tukorea.capstone.chat.vo.ChatMessageVo;
import ac.kr.tukorea.capstone.chat.vo.ChatRoomVo;
import ac.kr.tukorea.capstone.config.util.MessageForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/chat/room")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;
    private MessageForm messageForm;

    @PostMapping()
    public ResponseEntity<MessageForm> createChatRoom(@RequestBody ChatRoomCreateDto chatRoomCreateDto){
        long roomId = roomService.createChatRoom(chatRoomCreateDto);
        messageForm = new MessageForm(201, roomId, "success");

        return ResponseEntity.status(HttpStatus.CREATED).body(messageForm);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<MessageForm> getChatMessages(@PathVariable long roomId){
        List<ChatMessageVo> messages = roomService.getChatMessages(roomId);
        messageForm = new MessageForm(200, messages, "success");

        return ResponseEntity.status(HttpStatus.OK).body(messageForm);
    }

    @GetMapping("/sell")
    public ResponseEntity<MessageForm> getSellerChatRooms(@RequestParam String username){
        List<ChatRoomVo> chatRooms = roomService.getSellerRooms(username);
        messageForm = new MessageForm(200, chatRooms, "success");

        return ResponseEntity.status(HttpStatus.OK).body(messageForm);
    }
    @GetMapping("/buy")
    public ResponseEntity<MessageForm> getBuyerChatRooms(@RequestParam String username){
        List<ChatRoomVo> chatRooms = roomService.getBuyerRooms(username);
        messageForm = new MessageForm(200, chatRooms, "success");

        return ResponseEntity.status(HttpStatus.OK).body(messageForm);
    }

    @DeleteMapping
    public ResponseEntity deleteChatRoom(@RequestParam long roomId,
                                                      @RequestParam String type){
        roomService.deleteRooms(roomId, type);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
