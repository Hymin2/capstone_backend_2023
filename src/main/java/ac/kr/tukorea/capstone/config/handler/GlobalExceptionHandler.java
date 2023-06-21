package ac.kr.tukorea.capstone.config.handler;

import ac.kr.tukorea.capstone.config.Exception.*;
import ac.kr.tukorea.capstone.config.util.MessageForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /*
        [Exception] Refresh Token이 만료되었을 때
        401 error
    */
    @ExceptionHandler(RefreshTokenExpiredException.class)
    public ResponseEntity<MessageForm> handleRefreshTokenExpiredException(){
        log.info("Refresh Token이 존재하지 않음");
        MessageForm messageForm = new MessageForm(401, "Refresh token is expired", "failed");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(messageForm);
    }

    /*
        [Exception] Refresh Token이 유효하지 않을 때
        401 error
     */
    @ExceptionHandler(InvalidRefreshTokenException.class)
    public ResponseEntity<MessageForm> handleInvalidRefreshTokenException() {
        log.info("Refresh Token의 username과 요청한 username이 같지 않음");
        MessageForm messageForm = new MessageForm(401, "Refresh token is invalid", "failed");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(messageForm);
    }

    /*
        [Exception] Username을 찾을 수 없을 때
        404 error
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<MessageForm> handleUsernameNotFoundException() {
        log.info("username이 존재하지 않음");
        MessageForm messageForm = new MessageForm(404, "Username is not found", "failed");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageForm);
    }

    /*
        [Exception] Category를 찾을 수 없을 때
        404 error
     */
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<MessageForm> handleCategoryNotFoundException(){
        log.info("category가 존재하지 않음");
        MessageForm messageForm = new MessageForm(404, "Category is not found", "failed");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageForm);
    }
    /*
        [Exception] Market의 이름이 중복일 때
        409 error
     */
    @ExceptionHandler(DuplicateNickNameException.class)
    public ResponseEntity<MessageForm> handleDuplicateMarketNameException() {
        log.info("닉네임이 중복");
        MessageForm messageForm = new MessageForm(409, "Duplicate nickname", "failed");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(messageForm);
    }

    /*
        [Exception] User의 Market이 이미 존재할 때
        409 error
     */
    @ExceptionHandler(ExistingMarketOfUserException.class)
    public ResponseEntity<MessageForm> handleExistingMarketOfUserException() {
        log.info("Market을 생성하려는 User의 Market이 이미 존재");
        MessageForm messageForm = new MessageForm(409, "An existing market of user", "failed");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(messageForm);
    }

    /*
    // Runtime error
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<MessageForm> handleRuntimeErrorException(){
        log.info("기타 Runtime error");
        MessageForm messageForm = new MessageForm(500, "Runtime error", "failed");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageForm);
    }

     */
}
