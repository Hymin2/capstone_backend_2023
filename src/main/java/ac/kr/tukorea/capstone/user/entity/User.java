package ac.kr.tukorea.capstone.user.entity;

import ac.kr.tukorea.capstone.chat.entity.ChattingRoom;
import ac.kr.tukorea.capstone.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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

    @Column(name = "user_name", nullable = false, unique = true, length = 20)
    private String username;

    @Column(name = "user_password", nullable = false, length = 255)
    private String userPassword;

    @Column(name = "nickname", nullable = false, unique = true, length = 20)
    private String nickname;

    @Column(name = "phone_number", nullable = false, length = 13)
    private String phoneNumber;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "image_size")
    private long imageSize;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Post> posts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Authority> authorities;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private List<ChattingRoom> seller;

    @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL)
    private List<ChattingRoom> buyer;

    public void setEncodePassword(String password) {
        this.userPassword = password;
    }
    public void setAuthorities(List<Authority> authorities){this.authorities = authorities;}
    public void setImagePath(String imagePath){ this.imagePath = imagePath; }
    public void setImageSize(long imageSize) {this.imageSize = imageSize; }
    public void setNickname(String nickname){ this.nickname = nickname; }
}
