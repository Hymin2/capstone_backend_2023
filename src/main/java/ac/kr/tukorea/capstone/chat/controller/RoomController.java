package ac.kr.tukorea.capstone.chat.controller;

import ac.kr.tukorea.capstone.chat.repository.ChatRoomRepository;
import ac.kr.tukorea.capstone.config.util.MessageForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/chat")
@Log4j2
public class RoomController {

    private final ChatRoomRepository repository;
    private MessageForm messageForm = new MessageForm();

    //채팅방 목록 조회
    @GetMapping(value = "/rooms")
    public ModelAndView rooms(){

        log.info("# All Chat Rooms");
        ModelAndView mv = new ModelAndView("chat/rooms");

        mv.addObject("list", repository.findAllRooms());

        return mv;
    }

    //채팅방 개설
    @PostMapping(value = "/room")
    public ResponseEntity<MessageForm> create(@RequestParam String name){

        log.info("# Create Chat Room , name: " + name);
        return ResponseEntity.status(HttpStatus.CREATED).body(messageForm);
    }

    //채팅방 조회
    @GetMapping("/room")
    public void getRoom(String roomId, Model model){

        log.info("# get Chat Room, roomID : " + roomId);

        model.addAttribute("room", repository.findRoomById(roomId));
    }
}