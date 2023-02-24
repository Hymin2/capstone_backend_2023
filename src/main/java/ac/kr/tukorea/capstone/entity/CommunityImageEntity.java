package ac.kr.tukorea.capstone.entity;

import javax.persistence.*;

@Entity(name = "community_image")
@Table(name = "community_image_table")
public class CommunityImageEntity {
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
            name = "image_path",
            nullable = false
    )
    private String image_path;

    @Column(
            name = "image_size",
            nullable = false
    )
    private int image_size;

    @Column(
            name = "create_time",
            nullable = false
    )
    private String create_time;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private CommunityPostEntity post_id;
}
