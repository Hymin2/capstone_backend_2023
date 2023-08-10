package ac.kr.tukorea.capstone.chat.repository;

import ac.kr.tukorea.capstone.chat.vo.ChatRoomVo;

import java.util.List;

public interface ChatRoomCustomRepository {
    List<ChatRoomVo> getSellerRooms(long userId);
    List<ChatRoomVo> getBuyerRooms(long userId);
}
