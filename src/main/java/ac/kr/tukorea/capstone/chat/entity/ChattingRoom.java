package ac.kr.tukorea.capstone.chat.entity;

import ac.kr.tukorea.capstone.post.entity.Post;
import ac.kr.tukorea.capstone.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "chatting_room_table")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChattingRoom {
    @Id // 채팅방 고유 id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY) // 판매글 id 참조용
    @JoinColumn(name = "sale_post_id")
    private Post post;

    @Id // 판매 유저 id 참조용
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private User seller;

    @Id // 구매 유저 id 참조용
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private User buyer;

    @OneToMany(mappedBy = "chattingRoom", fetch = FetchType.LAZY)
    private List<ChattingContent> chattingContents;

    @OneToMany(mappedBy = "chattingRoomForUserId", fetch = FetchType.LAZY)
    private List<ChattingContent> chattingRoomForUserIds;






}
