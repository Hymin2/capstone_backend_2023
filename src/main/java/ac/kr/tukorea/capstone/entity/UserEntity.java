package ac.kr.tukorea.capstone.entity;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "user")
@Table(name = "user_table")
public class UserEntity {
    @Id
    @Column(
            name ="user_idx",
            nullable = false
    )
    @GeneratedValue(
        strategy =GenerationType.AUTO
    )
    private int user_idx;

    @Column(
            name = "user_id",
            nullable = false
    )
    private String user_id;

    @Column(
            name = "user_password",
            nullable = false
    )
    private String user_pw;

    @Column(
            name = "user_nickname",
            nullable = false
    )
    private String user_nickname;

    @Column(
            name = "phone_number",
            nullable = false
    )
    private String phone_number;

    @Column(
            name = "email_address",
            nullable = false
    )
    private String email_address;

    @OneToMany(mappedBy = "user_idx")
    private Set<CommunityPostEntity> community_posts;

    @OneToMany(mappedBy = "user_idx")
    private Set<CommunityCommentEntity> community_comments;

    @OneToMany(mappedBy = "user_idx")
    private Set<SalePostEntity> sale_posts;
}
