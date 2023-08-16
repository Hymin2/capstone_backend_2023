package ac.kr.tukorea.capstone.chat.controller;

import ac.kr.tukorea.capstone.chat.dto.ChatRoomCreateDto;
import ac.kr.tukorea.capstone.chat.dto.ChatRoomsDto;
import ac.kr.tukorea.capstone.chat.service.RoomService;
import ac.kr.tukorea.capstone.config.util.MessageForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/chat/room")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;
    private MessageForm messageForm;

    @PostMapping("/create")
    public ResponseEntity<MessageForm> createChatRoom(@RequestBody ChatRoomCreateDto chatRoomCreateDto){
        roomService.createChatRoom(chatRoomCreateDto);
        messageForm = new MessageForm(201, "Chatting room registration is success", "success");

        return ResponseEntity.status(HttpStatus.CREATED).body(messageForm);
    }

    @GetMapping("/seller")
    public ResponseEntity<MessageForm> getSellerChatRooms(@RequestParam long userId){
        ChatRoomsDto chatRooms = new ChatRoomsDto(roomService.getSellerRooms(userId));
        messageForm = new MessageForm(200, chatRooms, "success");

        return ResponseEntity.status(HttpStatus.OK).body(messageForm);
    }
    @GetMapping("/buyer")
    public ResponseEntity<MessageForm> getBuyerChatRooms(@RequestParam long userId){
        ChatRoomsDto chatRooms = new ChatRoomsDto(roomService.getBuyerRooms(userId));
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
