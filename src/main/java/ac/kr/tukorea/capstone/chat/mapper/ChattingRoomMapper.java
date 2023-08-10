package ac.kr.tukorea.capstone.chat.mapper;

import ac.kr.tukorea.capstone.chat.dto.ChatRoomCreateDto;
import ac.kr.tukorea.capstone.chat.entity.ChattingRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChattingRoomMapper {
    public ChattingRoom ChattingRoomCreateInfo(ChatRoomCreateDto chatRoomCreateDto){
        /*
        return ChattingRoom.builder()
                .post(chatRoomCreateDto.getSalePostId())
                .seller(chatRoomCreateDto.getSeller())
                .buyer(chatRoomCreateDto.getBuyer())
                .build();

         */

        return null;
    }
}
