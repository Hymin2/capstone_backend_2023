package ac.kr.tukorea.capstone.chat.service;

import ac.kr.tukorea.capstone.chat.dto.ChatRoomCreateDto;
import ac.kr.tukorea.capstone.chat.entity.ChattingRoom;
import ac.kr.tukorea.capstone.chat.repository.ChatRoomCustomRepository;
import ac.kr.tukorea.capstone.chat.repository.ChattingRoomRepository;
import ac.kr.tukorea.capstone.chat.vo.ChatRoomVo;
import ac.kr.tukorea.capstone.config.Exception.RoomNotFoundException;
import ac.kr.tukorea.capstone.post.entity.Post;
import ac.kr.tukorea.capstone.post.service.PostService;
import ac.kr.tukorea.capstone.user.entity.User;
import ac.kr.tukorea.capstone.user.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final UserService userService;
    private final PostService postService;
    private final ChattingRoomRepository chattingRoomRepository;
    private final ChatRoomCustomRepository chatRoomCustomRepository;

    @Transactional
    public void createChatRoom(ChatRoomCreateDto chatRoomCreateDto){
        User user = userService.getUserByUserId(chatRoomCreateDto.getBuyerId());
        Post post = postService.getPostByPostId(chatRoomCreateDto.getSalePostId());

        ChattingRoom chattingRoom = ChattingRoom.builder()
                .buyer(user)
                .post(post)
                .build();

        chattingRoomRepository.save(chattingRoom);
    }

    @Transactional
    public List<ChatRoomVo> getSellerRooms(long userId){
        return chatRoomCustomRepository.getSellerRooms(userId);
    }

    @Transactional
    public List<ChatRoomVo> getBuyerRooms(long userId){
        return chatRoomCustomRepository.getBuyerRooms(userId);
    }

    @Transactional
    public void additionSeller(long roomId, long sellerId){
        ChattingRoom chattingRoom = chattingRoomRepository.findById(roomId).orElseThrow(RoomNotFoundException::new);
        User seller = userService.getUserByUserId(sellerId);

        chattingRoom.setSeller(seller);
    }
}
