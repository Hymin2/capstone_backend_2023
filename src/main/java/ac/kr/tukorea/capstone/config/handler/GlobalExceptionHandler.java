package ac.kr.tukorea.capstone.config.handler;

import ac.kr.tukorea.capstone.config.util.MessageForm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<MessageForm> handleRuntimeErrorException(){
        MessageForm messageForm = new MessageForm(500, "Runtime error", "failed");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageForm);
    }

}
