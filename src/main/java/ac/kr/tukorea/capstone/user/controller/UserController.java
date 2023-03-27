package ac.kr.tukorea.capstone.user.controller;

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
    UserService userService;

    @PostMapping(value = "/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterDto userRegisterDto){
        User user = userService.registerUser(userRegisterDto);

        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공");
    }

    @GetMapping(value = "/register/check/id")
    public ResponseEntity<Boolean> checkIdDuplicate(@RequestParam String id){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.isDuplicateId(id));
    }

    @GetMapping(value = "/register/check/nickname")
    public ResponseEntity<Boolean> checkNicknameDuplicate(@RequestParam String nickname){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.isDuplicateNickname(nickname));
    }

}
