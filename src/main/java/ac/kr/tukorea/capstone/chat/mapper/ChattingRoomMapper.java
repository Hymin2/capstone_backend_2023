package ac.kr.tukorea.capstone.chat.mapper;

import ac.kr.tukorea.capstone.chat.dto.ChattingCreateDto;
import ac.kr.tukorea.capstone.chat.entity.ChattingRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChattingRoomMapper {
    public ChattingRoom ChattingRoomCreateInfo(ChattingCreateDto chattingCreateDto){
        return ChattingRoom.builder()
                .post(chattingCreateDto.getSale_post_id())
                .seller(chattingCreateDto.getSeller())
                .buyer(chattingCreateDto.getBuyer())
                .build();
    }
}
