package ac.kr.tukorea.capstone.config.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MessageForm {
    private int status;
    private String message;
    private String result;

    public void setMessageForm(int status, String message, String result){
        this.status = status;
        this.message = message;
        this.result = result;
    }
}
