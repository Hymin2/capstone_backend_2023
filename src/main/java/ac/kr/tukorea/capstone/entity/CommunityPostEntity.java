package ac.kr.tukorea.capstone.entity;

import net.bytebuddy.dynamic.loading.InjectionClassLoader;
import org.apache.catalina.User;

import javax.persistence.*;
import java.sql.Time;
import java.util.Set;

@Entity(name = "community_post")
@Table(name = "community_post_table")
public class CommunityPostEntity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    @Column(
            name = "community_post_id",
            nullable = false
    )
    private int community_post_id;

    @Column(
            name = "post_title",
            nullable = false
    )
    private String post_title;

    @Column(
            name = "post_contents",
            nullable = false
    )
    private String post_contents;

    @Column(
            name = "create_time",
            nullable = false
    )
    private String create_time;

    @Column(
            name = "update_time",
            nullable = false
    )
    private String update_time;


    @OneToMany(mappedBy = "post_id")
    private Set<CommunityImageEntity> images;

    @OneToMany(mappedBy = "post_id")
    private Set<CommunityCommentEntity> comments;

    @ManyToOne
    @JoinColumn(name = "user_idx")
    private UserEntity user_idx;

}
