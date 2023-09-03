package ac.kr.tukorea.capstone.chat.service;

import ac.kr.tukorea.capstone.chat.entity.ChattingRoom;
import ac.kr.tukorea.capstone.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {
    private final RoomService roomService;
    private final PostService postService;

    @Transactional
    public boolean dealComplete(long roomId, long postId, String userType) {
        ChattingRoom chattingRoom = roomService.getRoom(roomId);

        if(userType.equals("Buyer") && !chattingRoom.isBuyerDealCompleted()){
            chattingRoom.setBuyerDealCompleted(true);

            if(chattingRoom.isSellerDealCompleted()){
                postService.setPostIsNotOnSale(postId);

                return true;
            }
        } else if (userType.equals("Seller") && !chattingRoom.isSellerDealCompleted()){
            chattingRoom.setSellerDealCompleted(true);

            if(chattingRoom.isBuyerDealCompleted()){
                postService.setPostIsNotOnSale(postId);

                return true;
            }
        }

        return false;
    }

}
