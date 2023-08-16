package ac.kr.tukorea.capstone.chat.service;

import ac.kr.tukorea.capstone.chat.dto.ChatRoomCreateDto;
import ac.kr.tukorea.capstone.chat.dto.ChattingMessageDto;
import ac.kr.tukorea.capstone.chat.entity.ChattingContent;
import ac.kr.tukorea.capstone.chat.entity.ChattingRoom;
import ac.kr.tukorea.capstone.chat.mapper.ChattingContentMapper;
import ac.kr.tukorea.capstone.chat.mapper.ChattingRoomMapper;
import ac.kr.tukorea.capstone.chat.repository.ChattingContentRepository;
import ac.kr.tukorea.capstone.chat.repository.ChattingRoomRepository;
import ac.kr.tukorea.capstone.post.service.PostService;
import ac.kr.tukorea.capstone.user.entity.User;
import ac.kr.tukorea.capstone.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {
    private final RoomService roomService;
    private final PostService postService;

    @Transactional
    public boolean dealComplete(long roomId, long postId, String messageType) {
        ChattingRoom chattingRoom = roomService.getRoom(roomId);

        if(messageType.equals("Buyer") && !chattingRoom.isBuyerDealCompleted()){
            chattingRoom.setBuyerDealCompleted(true);

            if(chattingRoom.isSellerDealCompleted()){
                postService.setPostIsNotOnSale(postId);

                return true;
            }
        } else if (messageType.equals("Seller") && !chattingRoom.isSellerDealCompleted()){
            chattingRoom.setSellerDealCompleted(true);

            if(chattingRoom.isBuyerDealCompleted()){
                postService.setPostIsNotOnSale(postId);

                return true;
            }
        }

        return false;
    }

}
