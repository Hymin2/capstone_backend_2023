package ac.kr.tukorea.capstone.chat.repository;


import ac.kr.tukorea.capstone.chat.entity.QChattingMessage;
import ac.kr.tukorea.capstone.chat.entity.QChattingRoom;
import ac.kr.tukorea.capstone.chat.vo.ChatMessageVo;
import ac.kr.tukorea.capstone.chat.vo.ChatRoomVo;
import ac.kr.tukorea.capstone.user.entity.QUser;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatRoomImplRepository implements ChatRoomCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;
    private final QChattingRoom chattingRoom = QChattingRoom.chattingRoom;
    private final QUser user = QUser.user;

    @Override
    public List<ChatRoomVo> getSellerRooms(String username) {
        List<ChatRoomVo> chatRooms = jpaQueryFactory
                .select(Projections.constructor(ChatRoomVo.class, chattingRoom.id, chattingRoom.post.id, user.username, user.nickname, user.imagePath))
                .from(chattingRoom)
                .innerJoin(user)
                .on(user.eq(chattingRoom.buyer))
                .where(chattingRoom.seller.username.eq(username))
                .fetch();

        return chatRooms;
    }

    @Override
    public List<ChatRoomVo> getBuyerRooms(String username) {
        List<ChatRoomVo> chatRooms = jpaQueryFactory
                .select(Projections.constructor(ChatRoomVo.class, chattingRoom.id, chattingRoom.post.id, user.username, user.nickname, user.imagePath))
                .from(chattingRoom)
                .innerJoin(user)
                .on(user.eq(chattingRoom.seller))
                .where(chattingRoom.buyer.username.eq(username))
                .fetch();

        return chatRooms;
    }

    @Override
    public ChatRoomVo getChatRoom(long roomId) {
        ChatRoomVo chatRoom = jpaQueryFactory
                .select(Projections.constructor(ChatRoomVo.class, chattingRoom.id, chattingRoom.post, user.username, user.nickname, user.imagePath))
                .from(chattingRoom)
                .innerJoin(user)
                .on(chattingRoom.buyer.eq(user))
                .where(chattingRoom.id.eq(roomId))
                .limit(1)
                .fetchOne();

        return chatRoom;
    }
}
