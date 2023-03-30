package ac.kr.tukorea.capstone.user.controller;

import ac.kr.tukorea.capstone.config.auth.UserDetailsImplService;
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
    private final UserDetailsImplService userDetailsImplService;

    @PostMapping(value = "/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterDto userRegisterDto){
        if(userService.isDuplicateId(userRegisterDto.getUsername()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("아이디가 이미 존재합니다.");

        else if(userService.isDuplicateNickname(userRegisterDto.getNickname()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("닉네임이 이미 존재합니다.");
        else {
            User user = userService.registerUser(userRegisterDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공");
        }
    }

    @GetMapping(value = "/register/check/username")
    public ResponseEntity<String> checkIdDuplicate(@RequestParam String username){
        if(!userService.isDuplicateId(username)) return ResponseEntity.ok("사용가능한 아이디입니다.");
        else return ResponseEntity.ok("아이디가 이미 존재합니다.");
    }

    @GetMapping(value = "/register/check/nickname")
    public ResponseEntity<Boolean> checkNicknameDuplicate(@RequestParam String nickname){
        return ResponseEntity.ok(!userService.isDuplicateNickname(nickname));
    }

}
