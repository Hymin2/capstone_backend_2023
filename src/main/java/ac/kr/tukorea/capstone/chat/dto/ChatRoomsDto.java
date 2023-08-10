package ac.kr.tukorea.capstone.chat.dto;

import ac.kr.tukorea.capstone.chat.vo.ChatRoomVo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChatRoomsDto {
    private List<ChatRoomVo> chatRooms;
}
