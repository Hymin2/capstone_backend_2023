package ac.kr.tukorea.capstone.chat.repository;

import ac.kr.tukorea.capstone.chat.vo.ChatMessageVo;
import ac.kr.tukorea.capstone.chat.vo.ChatRoomVo;

import java.util.List;

public interface ChatRoomCustomRepository {
    List<ChatRoomVo> getSellerRooms(String username);
    List<ChatRoomVo> getBuyerRooms(String username);
    List<ChatMessageVo> getChatMessages(long roomId);
}
