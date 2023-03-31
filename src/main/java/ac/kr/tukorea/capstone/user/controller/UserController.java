package ac.kr.tukorea.capstone.user.controller;

import ac.kr.tukorea.capstone.config.auth.UserDetailsImplService;
import ac.kr.tukorea.capstone.config.util.MessageForm;
import ac.kr.tukorea.capstone.user.dto.UserLoginDto;
import ac.kr.tukorea.capstone.user.dto.UserRegisterDto;
import ac.kr.tukorea.capstone.user.entity.User;
import ac.kr.tukorea.capstone.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private MessageForm messageForm = new MessageForm();

    @PostMapping(value = "/register")
    public ResponseEntity<MessageForm> register(@RequestBody UserRegisterDto userRegisterDto){
        if(userService.isDuplicateId(userRegisterDto.getUsername())) {
            messageForm.setMessageForm(409, "Duplicated id", "failed");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(messageForm);
        }

        else if(userService.isDuplicateNickname(userRegisterDto.getNickname())) {
            messageForm.setMessageForm(409, "Duplicated nickname", "failed");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(messageForm);
        }
        else {
            User user = userService.registerUser(userRegisterDto);

            messageForm.setMessageForm(201, "Registration success", "success");
            return ResponseEntity.status(HttpStatus.CREATED).body(messageForm);
        }
    }

    @GetMapping(value = "/register/check/username")
    public ResponseEntity<MessageForm> checkIdDuplicate(@RequestParam String username){
        if(!userService.isDuplicateId(username))
            messageForm.setMessageForm(200, "Available id", "success");
        else{
            messageForm.setMessageForm(200, "Duplicated id", "success");
        }
        return ResponseEntity.status(HttpStatus.OK).body(messageForm);
    }

    @GetMapping(value = "/register/check/nickname")
    public ResponseEntity<MessageForm> checkNicknameDuplicate(@RequestParam String nickname){
        if(!userService.isDuplicateNickname(nickname))
            messageForm.setMessageForm(200, "Available id", "success");
        else{
            messageForm.setMessageForm(200, "Duplicated id", "success");
        }
        return ResponseEntity.status(HttpStatus.OK).body(messageForm);
    }


}
