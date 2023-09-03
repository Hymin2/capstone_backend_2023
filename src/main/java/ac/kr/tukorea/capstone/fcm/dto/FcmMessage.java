package ac.kr.tukorea.capstone.fcm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class FcmMessage {
    private boolean validateOnly;
    private Message message;

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Message{
        private Data data;
        private String token;
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Data{
        private long roomId;
        private long postId;
        private String username;
        private String nickname;
        private String userImage;
        private String userType;
        private String message;
        private String messageType;
        private String datetime;
    }
}
