package ac.kr.tukorea.capstone.user.controller;

import ac.kr.tukorea.capstone.user.dto.UserRegisterDto;
import ac.kr.tukorea.capstone.user.entity.User;
import ac.kr.tukorea.capstone.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(name = "/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    UserService userService;

    @PostMapping(name = "/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterDto userRegisterDto){
        User user = userService.registerUser(userRegisterDto);

        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공");
    }
}
