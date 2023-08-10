package ac.kr.tukorea.capstone.chat.repository;


import ac.kr.tukorea.capstone.chat.entity.QChattingContent;
import ac.kr.tukorea.capstone.chat.entity.QChattingRoom;
import ac.kr.tukorea.capstone.chat.vo.ChatRoomVo;
import ac.kr.tukorea.capstone.user.entity.QUser;
import com.querydsl.core.QueryFactory;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
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
    private final QChattingContent chattingContent = QChattingContent.chattingContent;
    private final QUser user = QUser.user;

    @Override
    public List<ChatRoomVo> getSellerRooms(long userId) {
        List<ChatRoomVo> chatRooms = jpaQueryFactory
                .select(Projections.constructor(ChatRoomVo.class, chattingRoom.id, user.nickname, chattingContent.content, chattingContent.createdAt))
                .from(chattingRoom)
                .innerJoin(user)
                .on(user.eq(chattingRoom.buyer))
                .innerJoin(chattingContent)
                .on(chattingContent.id.eq(
                        JPAExpressions
                                .select(chattingContent.id)
                                .from(chattingContent)
                                .where(chattingContent.chattingRoom.eq(chattingRoom))
                                .orderBy(chattingContent.createdAt.desc())
                                .limit(1)))
                .where(chattingRoom.seller.id.eq(userId))
                .fetch();

        return chatRooms;
    }

    @Override
    public List<ChatRoomVo> getBuyerRooms(long userId) {
        List<ChatRoomVo> chatRooms = jpaQueryFactory
                .select(Projections.constructor(ChatRoomVo.class, chattingRoom.id, user.nickname, chattingContent.content, chattingContent.createdAt))
                .from(chattingRoom)
                .innerJoin(user)
                .on(user.eq(chattingRoom.seller))
                .innerJoin(chattingContent)
                .on(chattingContent.id.eq(
                        JPAExpressions
                                .select(chattingContent.id)
                                .from(chattingContent)
                                .where(chattingContent.chattingRoom.eq(chattingRoom))
                                .orderBy(chattingContent.createdAt.desc())
                                .limit(1)))
                .where(chattingRoom.buyer.id.eq(userId))
                .fetch();

        return chatRooms;
    }
}
