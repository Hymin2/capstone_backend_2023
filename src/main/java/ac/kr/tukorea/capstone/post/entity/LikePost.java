package ac.kr.tukorea.capstone.post.entity;

import ac.kr.tukorea.capstone.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "like_post_table")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class LikePost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
