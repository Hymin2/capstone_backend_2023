package ac.kr.tukorea.capstone.entity;

import javax.persistence.*;
import java.util.Set;
@Entity(name = "chatting_room")
@Table(name = "chatting_room_table")
public class ChattingRoomEntity {
    @Id
    @Column(
            name = "chatting_room_idx",
            nullable = false
    )
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    private int chatting_room_idx;
    @OneToMany(mappedBy = "chatting_join_idx")
    private Set<ChattingJoinEntity> chatting_join_idx;

    @Column(
            name = "create_time",
            nullable = false
    )
    private String create_time;

    @Column(
            name = "status",
            nullable = false
    )
    private String status;

}
