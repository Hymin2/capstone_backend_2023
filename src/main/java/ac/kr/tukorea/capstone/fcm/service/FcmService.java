package ac.kr.tukorea.capstone.fcm.service;

import ac.kr.tukorea.capstone.fcm.dto.FcmMessage;
import ac.kr.tukorea.capstone.fcm.entity.FcmToken;
import ac.kr.tukorea.capstone.fcm.repository.FcmTokenRedisRepository;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FcmService {
    private final String API_URL = "https://fcm.googleapis.com/v1/projects/capstone-e5663/messages:send";
    private final ObjectMapper objectMapper;
    private final FcmTokenRedisRepository fcmTokenRedisRepository;

    public void saveFcmToken(String username, String fcmToken){
        fcmTokenRedisRepository.save(username, fcmToken);
    }

    public Optional<FcmToken> getFcmToken(String username){
        return fcmTokenRedisRepository.findByUsername(username);
    }

    public void deleteFcmToken(String username){
        fcmTokenRedisRepository.deleteByUsername(username);
    }

    public void sendMessageTo(String targetToken, String message) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message,
                MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        Response response = client.newCall(request).execute();

        System.out.println(response.body().string());
    }

    private String makeMessage(String targetToken, long roomId, long postId, String username, String nickname,
                               String userImage, String userType, String message, String messageType, String datetime) throws JsonParseException, JsonProcessingException {
        FcmMessage fcmMessage = FcmMessage.builder()
                .message(FcmMessage.Message.builder()
                        .token(targetToken)
                        .data(FcmMessage.Data.builder()
                                .roomId(roomId)
                                .postId(postId)
                                .username(username)
                                .nickname(nickname)
                                .userImage(userImage)
                                .userType(userType)
                                .message(message)
                                .messageType(messageType)
                                .datetime(datetime)
                                .build()
                        ).build()).validateOnly(false).build();

        return objectMapper.writeValueAsString(fcmMessage);
    }

    private String getAccessToken() throws IOException{
        String firebaseConfigPath = "firebase/firebase_service_key.json";

        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

        googleCredentials.refreshIfExpired();

        return googleCredentials.getAccessToken().getTokenValue();
    }
}
