package ac.kr.tukorea.capstone.entity;

import javax.persistence.*;

@Entity(name = "community_comment")
@Table(name = "community_comment_table")
public class CommunityCommentEntity {
    @Id
    @Column(
            name = "id",
            nullable = false
    )
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    private int id;

    @Column(
            name = "comment",
            nullable = false
    )
    private String comment;

    @Column(
            name = "create_time",
            nullable = false
    )
    private String create_time;

    @Column(
            name = "is_comment",
            nullable = false
    )
    private String is_comment;

    @Column(
            name = "order",
            nullable = false
    )
    private int order;


    @ManyToOne
    @JoinColumn(name = "post_id")
    private CommunityPostEntity post_id;

    @ManyToOne
    @JoinColumn(name = "user_idx")
    private UserEntity user_idx;


}
