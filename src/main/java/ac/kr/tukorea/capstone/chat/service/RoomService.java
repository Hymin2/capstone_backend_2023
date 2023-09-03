package ac.kr.tukorea.capstone.chat.service;

import ac.kr.tukorea.capstone.chat.dto.ChatRoomCreateDto;
import ac.kr.tukorea.capstone.chat.dto.ChattingMessageDto;
import ac.kr.tukorea.capstone.chat.entity.ChattingRoom;
import ac.kr.tukorea.capstone.chat.repository.ChatRoomCustomRepository;
import ac.kr.tukorea.capstone.chat.repository.ChattingRoomRepository;
import ac.kr.tukorea.capstone.chat.vo.ChatMessageVo;
import ac.kr.tukorea.capstone.chat.vo.ChatRoomVo;
import ac.kr.tukorea.capstone.config.Exception.NotFoundRoomException;
import ac.kr.tukorea.capstone.post.entity.Post;
import ac.kr.tukorea.capstone.post.service.PostService;
import ac.kr.tukorea.capstone.user.entity.User;
import ac.kr.tukorea.capstone.user.service.UserService;
import lombok.RequiredArgsConstructor;
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
    public long createChatRoom(ChatRoomCreateDto chatRoomCreateDto){
        User buyer = userService.getUserByUsername(chatRoomCreateDto.getBuyerUsername());
        User seller = userService.getUserByUsername(chatRoomCreateDto.getSellerUsername());
        Post post = postService.getPostByPostId(chatRoomCreateDto.getSalePostId());

        ChattingRoom chattingRoom = ChattingRoom.builder()
                .buyer(buyer)
                .seller(seller)
                .post(post)
                .isBuyerDealCompleted(false)
                .isSellerDealCompleted(false)
                .build();

        chattingRoomRepository.save(chattingRoom);

        return chattingRoom.getId();
    }

    @Transactional
    public List<ChatRoomVo> getSellerRooms(String username){
        return chatRoomCustomRepository.getSellerRooms(username);
    }

    @Transactional
    public List<ChatRoomVo> getBuyerRooms(String username){
        return chatRoomCustomRepository.getBuyerRooms(username);
    }

    @Transactional
    public void deleteRooms(long roomId, String type){
        ChattingRoom chattingRoom = chattingRoomRepository.findById(roomId).orElseThrow(NotFoundRoomException::new);

        if(chattingRoom.getBuyer() == null || chattingRoom.getSeller() == null)
            chattingRoomRepository.deleteById(roomId);

        if(type.equals("buyer"))
            chattingRoom.setBuyer(null);
        else chattingRoom.setSeller(null);

    }

    public ChattingRoom getRoom(long roomId){
        return chattingRoomRepository.findById(roomId).orElseThrow(NotFoundRoomException::new);
    }

    public List<ChatMessageVo> getChatMessages(long roomId) {
        List<ChatMessageVo> messages = chatRoomCustomRepository.getChatMessages(roomId);

        return messages;
    }
}
