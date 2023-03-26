package ac.kr.tukorea.capstone.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "user")
@Table(name = "user_table")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "user_id", nullable = false, unique = true, length = 20)
    private String userId;

    @Column(name = "user_password", nullable = false, length = 255)
    private String userPassword;

    @Column(name = "nickname", nullable = false, unique = true, length = 20)
    private String nickname;

    @Column(name = "phone_number", nullable = false, length = 13)
    private String phoneNumber;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    public void setEncodePassword(String password) {
        this.userPassword = password;
    }
}
