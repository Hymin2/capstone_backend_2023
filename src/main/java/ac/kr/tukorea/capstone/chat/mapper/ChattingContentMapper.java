package ac.kr.tukorea.capstone.chat.mapper;

import ac.kr.tukorea.capstone.chat.dto.ChattingMessageDto;
import ac.kr.tukorea.capstone.chat.entity.ChattingContent;
import ac.kr.tukorea.capstone.chat.entity.ChattingRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChattingContentMapper {
    public ChattingContent ChattingContentCreateInfo(ChattingMessageDto chattingMessageDto){
        /*
        return ChattingContent.builder()
                .content(chattingMessageDto.getContent())
                .chattingRoomForUserId(chattingMessageDto.getUserId())
                .chattingRoom(chattingMessageDto.getRoomId())
                .build();


         */

        return null;
    }
}
