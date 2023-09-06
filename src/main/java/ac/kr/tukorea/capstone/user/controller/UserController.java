package ac.kr.tukorea.capstone.user.controller;

import ac.kr.tukorea.capstone.config.jwt.JwtTokenService;
import ac.kr.tukorea.capstone.config.util.MessageForm;
import ac.kr.tukorea.capstone.user.dto.FollowListDto;
import ac.kr.tukorea.capstone.user.dto.FollowRegisterDto;
import ac.kr.tukorea.capstone.user.dto.UserInfoDto;
import ac.kr.tukorea.capstone.user.dto.UserRegisterDto;
import ac.kr.tukorea.capstone.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping(value = "/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtTokenService jwtTokenService;
    private MessageForm messageForm = new MessageForm();

    @PostMapping(value = "/register")
    public ResponseEntity<MessageForm> register(@RequestBody UserRegisterDto userRegisterDto){

        try {
            userService.registerUser(userRegisterDto);

            messageForm.setMessageForm(201, "Registration success", "success");
            return ResponseEntity.status(HttpStatus.CREATED).body(messageForm);
        }catch (RuntimeException e){
            messageForm.setMessageForm(409, "Registration failed", "failed");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(messageForm);
        }
    }

    @GetMapping(value = "/register/check/username")
    public ResponseEntity<MessageForm> checkIdDuplicate(@RequestParam String username){
        if(!userService.isDuplicateId(username)) {
            messageForm.setMessageForm(200, "Available id", "success");
            return ResponseEntity.status(HttpStatus.OK).body(messageForm);
        }
        else{
            messageForm.setMessageForm(409, "Duplicated id", "success");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(messageForm);
        }
    }

    @GetMapping(value = "/register/check/nickname")
    public ResponseEntity<MessageForm> checkNicknameDuplicate(@RequestParam String nickname){
        if(!userService.isDuplicateNickname(nickname)) {
            messageForm.setMessageForm(200, "Available id", "success");
            return ResponseEntity.status(HttpStatus.OK).body(messageForm);
        }
        else{
            messageForm.setMessageForm(409, "Duplicated id", "success");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(messageForm);
        }

    }

    @DeleteMapping(value = "/logout")
    public ResponseEntity logout(@RequestHeader("Authorization-refresh") String refreshToken,
                                  @RequestParam("username") String username){
        userService.logout(refreshToken, username);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping(value = "{username}")
    public ResponseEntity<MessageForm> updateNickname(@PathVariable String username,
                                                      @RequestBody String nickname){
        userService.updateNickname(username, nickname.replace("\"", ""));
        messageForm = new MessageForm(201, "Updating nickname is success", "success");

        return ResponseEntity.status(HttpStatus.CREATED).body(messageForm);
    }

    @PostMapping(value = "{username}")
    public ResponseEntity<MessageForm> uploadProfileImage(@PathVariable String username,
                                                          @RequestParam MultipartFile multipartFile){
        userService.uploadImage(multipartFile, username);
        messageForm = new MessageForm(201, "Uploading profile image is success", "success");

        return ResponseEntity.status(HttpStatus.CREATED).body(messageForm);
    }

    @GetMapping(value = "{username}")
    public ResponseEntity<MessageForm> getUserInfo(@PathVariable String username,
                                                   @RequestParam(required = false) String otherUsername){
        UserInfoDto userInfoDto = userService.getUserInfo(username, otherUsername);
        messageForm = new MessageForm(200, userInfoDto, "success");

        return ResponseEntity.status(HttpStatus.OK).body(messageForm);
    }

    @PostMapping(value = "/follow")
    public ResponseEntity<MessageForm> registerFollow(@RequestBody FollowRegisterDto followRegisterDto){
        userService.registerFollow(followRegisterDto);
        messageForm = new MessageForm(200, "Registration follow is success", "success");

        return ResponseEntity.status(HttpStatus.CREATED).body(messageForm);
    }



    @GetMapping(value = "/follow/following")
    public ResponseEntity<MessageForm> getFollowingList(@RequestParam String username){
        FollowListDto followList = userService.getFollowingList(username);
        messageForm = new MessageForm(200, followList, "success");

        return ResponseEntity.status(HttpStatus.OK).body(messageForm);
    }

    @GetMapping(value = "/follow/follower")
    public ResponseEntity<MessageForm> getFollowedList(@RequestParam String username){
        FollowListDto followList = userService.getFollowerList(username);
        messageForm = new MessageForm(200, followList, "success");

        return ResponseEntity.status(HttpStatus.OK).body(messageForm);
    }

    @DeleteMapping(value = "/follow")
    public ResponseEntity deleteFollow(@RequestParam String followingUsername, @RequestParam String followerUsername){
        userService.deleteFollow(followingUsername, followerUsername);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/img")
    public ResponseEntity<Resource> image(@RequestParam String name){
        Resource resource = userService.getUserImage(name);

        HttpHeaders headers = new HttpHeaders();
        Path path = null;

        try{
            path = Paths.get(resource.getURI());
            headers.add("Content-Type", Files.probeContentType(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new ResponseEntity(resource, headers, HttpStatus.OK);
    }

    @GetMapping(value = "/refresh")
    public void refreshAccessToken(HttpServletResponse response, HttpServletRequest request, @RequestParam String username) throws IOException {
        String token = jwtTokenService.refreshAccessToken(request, username);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", 200);
        jsonObject.put("result", "success");
        jsonObject.put("access_token", token);

        response.getWriter().print(jsonObject);
        response.getWriter().flush();
    }
}
