package ac.kr.tukorea.capstone.fcm.controller;

import ac.kr.tukorea.capstone.config.util.MessageForm;
import ac.kr.tukorea.capstone.fcm.service.FcmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/fcm")
@RequiredArgsConstructor
public class FcmController {
    private final FcmService fcmService;

    @PutMapping("/{username}")
    public ResponseEntity getFcmToken(@PathVariable String username, @RequestBody String fcmToken){
        fcmService.saveFcmToken(username, fcmToken);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
