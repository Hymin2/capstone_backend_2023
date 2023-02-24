package ac.kr.tukorea.capstone.entity;

import javax.persistence.*;
import java.util.Set;
@Entity(name = "chatting_join")
@Table(name = "chatting_join_table")
public class ChattingJoinEntity {
    @Id
    @ManyToOne
    @JoinColumn(name = "chatting_join_idx")
    private ChattingRoomEntity chatting_join_idx;

    @OneToMany(mappedBy = "chatting_room_idx")
    private Set<ChattingContentEntity> chatting_room_idx;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_idx")
    private UserEntity user_idx;



}
