package ac.kr.tukorea.capstone.entity;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "chatting_content")
@Table(name = "chatting_content_table")
public class ChattingContentEntity {
    @Id
    @Column(
            name = "chatting_content_idx",
            nullable = false
    )
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    private String chatting_content_idx;

    @ManyToOne
    @JoinColumn(name = "chatting_room_idx")
    private ChattingJoinEntity chatting_room_idx;

    @Column(
            name = "content",
            nullable = false
    )
    private String content;

    @Column(
            name = "create_time",
            nullable = false
    )
    private String create_time;
}

