package ac.kr.tukorea.capstone.entity;

import javax.persistence.*;

@Entity(name = "sale_post")
@Table(name = "sale_post_table")
public class SalePostEntity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    @Column(
            name = "sale_post_id",
            nullable = false
    )
    private int sale_post_id;

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
            name = "is_reply",
            nullable = false
    )
    private String is_reply;

    @Column(
            name = "order",
            nullable = false
    )
    private int order;
}
