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

        if(userType.equals("buy") && !chattingRoom.isBuyerDealCompleted()){
            chattingRoom.setBuyerDealCompleted(true);
            System.out.println("구매자 거래 완료");
            if(chattingRoom.isSellerDealCompleted()){
                postService.setPostIsNotOnSale(postId);
                System.out.println("판매자 거래 완료 상태에서 구매자 거래 완료");
                return true;
            }
        } else if (userType.equals("sell") && !chattingRoom.isSellerDealCompleted()){
            chattingRoom.setSellerDealCompleted(true);
            System.out.println("판매자 거래 완료");
            if(chattingRoom.isBuyerDealCompleted()){
                postService.setPostIsNotOnSale(postId);
                System.out.println("구매자 거래 완료 상태에서 판매자 거래 완료");
                return true;
            }
        }

        return false;
    }

}
