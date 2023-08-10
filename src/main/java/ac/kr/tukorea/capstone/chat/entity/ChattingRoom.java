package ac.kr.tukorea.capstone.chat.entity;

import ac.kr.tukorea.capstone.post.entity.Post;
import ac.kr.tukorea.capstone.user.entity.User;
import lombok.*;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private User seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private User buyer;

    @OneToMany(mappedBy = "chattingRoom", fetch = FetchType.LAZY)
    private List<ChattingContent> chattingContents;

    public void setSeller(User seller){
        this.seller = seller;
    }
}
