package ac.kr.tukorea.capstone.config.handler;

import ac.kr.tukorea.capstone.config.Exception.InvalidRefreshTokenException;
import ac.kr.tukorea.capstone.config.Exception.RefreshTokenExpiredException;
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

    // Runtime error
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<MessageForm> handleRuntimeErrorException(){
        log.info("기타 Runtime error");
        MessageForm messageForm = new MessageForm(500, "Runtime error", "failed");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageForm);
    }
}
