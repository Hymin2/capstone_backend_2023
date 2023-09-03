package ac.kr.tukorea.capstone.chat.mapper;

import ac.kr.tukorea.capstone.chat.dto.ChattingMessageDto;
import ac.kr.tukorea.capstone.chat.entity.ChattingMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChattingContentMapper {
    public ChattingMessage ChattingContentCreateInfo(ChattingMessageDto chattingMessageDto){
        /*
        return ChattingMessage.builder()
                .content(chattingMessageDto.getContent())
                .chattingRoomForUserId(chattingMessageDto.getUserId())
                .chattingRoom(chattingMessageDto.getRoomId())
                .build();


         */

        return null;
    }
}
