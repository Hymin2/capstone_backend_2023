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
    private final QChattingMessage chattingContent = QChattingMessage.chattingMessage;
    private final QUser user = QUser.user;

    @Override
    public List<ChatRoomVo> getSellerRooms(String username) {
        List<ChatRoomVo> chatRooms = jpaQueryFactory
                .select(Projections.constructor(ChatRoomVo.class, chattingRoom.id, chattingRoom.post.id, user.username, user.nickname, user.imagePath, chattingContent.content, chattingContent.createdAt.stringValue()))
                .from(chattingRoom)
                .innerJoin(user)
                .on(user.eq(chattingRoom.buyer))
                .leftJoin(chattingContent)
                .on(chattingContent.id.eq(
                        JPAExpressions
                                .select(chattingContent.id)
                                .from(chattingContent)
                                .where(chattingContent.chattingRoom.eq(chattingRoom))
                                .orderBy(chattingContent.createdAt.desc())
                                .limit(1)))
                .where(chattingRoom.seller.username.eq(username))
                .fetch();

        return chatRooms;
    }

    @Override
    public List<ChatRoomVo> getBuyerRooms(String username) {
        List<ChatRoomVo> chatRooms = jpaQueryFactory
                .select(Projections.constructor(ChatRoomVo.class, chattingRoom.id, chattingRoom.post.id, user.username, user.nickname, user.imagePath, chattingContent.content, chattingContent.createdAt.stringValue()))
                .from(chattingRoom)
                .innerJoin(user)
                .on(user.eq(chattingRoom.seller))
                .leftJoin(chattingContent)
                .on(chattingContent.id.eq(
                        JPAExpressions
                                .select(chattingContent.id)
                                .from(chattingContent)
                                .where(chattingContent.chattingRoom.eq(chattingRoom))
                                .orderBy(chattingContent.createdAt.desc())
                                .limit(1)))
                .where(chattingRoom.buyer.username.eq(username))
                .fetch();

        return chatRooms;
    }

    @Override
    public List<ChatMessageVo> getChatMessages(long roomId) {
        List<ChatMessageVo> messages = jpaQueryFactory
                .select(Projections.constructor(ChatMessageVo.class, chattingContent.messageType, chattingContent.content, chattingContent.sendUser.username, chattingContent.createdAt))
                .from(chattingContent)
                .innerJoin(chattingRoom)
                .on(chattingContent.chattingRoom.eq(chattingRoom))
                .where(chattingContent.chattingRoom.id.eq(roomId))
                .fetch();

        return messages;
    }
}
