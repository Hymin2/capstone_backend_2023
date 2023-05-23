package ac.kr.tukorea.capstone.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "follow_table")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "followed_user_id")
    private User followedUser;

    @ManyToOne
    @JoinColumn(name = "following_user_id")
    private User followingUser;
}
