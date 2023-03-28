package ac.kr.tukorea.capstone.user.service;

import ac.kr.tukorea.capstone.user.dto.UserRegisterDto;
import ac.kr.tukorea.capstone.user.entity.User;
import ac.kr.tukorea.capstone.user.repository.UserJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.*;


@DisplayName("로그인 및 회원가입 기능 테스트")
@SpringBootTest
public class UserServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    UserJpaRepository userJpaRepository;

    @DisplayName("회원가입 성공 테스트")
    @Test
    void successRegister(){
        UserRegisterDto userRegisterDto = new UserRegisterDto("유저ID", "password", "nickname", "phone number", "abcd@ab.cd");

        userService.registerUser(userRegisterDto);

        User user2 = userJpaRepository.findAll().get(0);

        assertThat(user2.getUserName()).isEqualTo(userRegisterDto.getUserId());
        assertThat(user2.getUserPassword()).isEqualTo(userRegisterDto.getPassword());
        assertThat(user2.getNickname()).isEqualTo(userRegisterDto.getNickname());
        assertThat(user2.getEmail()).isEqualTo(userRegisterDto.getEmail());
        assertThat(user2.getPhoneNumber()).isEqualTo(userRegisterDto.getPhoneNumber());

        userJpaRepository.delete(user2);

        System.out.println("회원가입 테스트 성공!");
    }

    @DisplayName("패스워드가 암호화되서 저장되는지 테스트")
    @Test
    void saveEncodedPassword(){
        UserRegisterDto userRegisterDto = new UserRegisterDto("유저ID", "password", "nickname", "phone number", "abcd@ab.cd");
        userService.registerUser(userRegisterDto);

        User user = userJpaRepository.findAll().get(0);

        assertThat("password").isNotEqualTo(user.getUserPassword());

        userJpaRepository.delete(user);
    }

    @DisplayName("ID가 중복일 때 중복확인 테스트")
    @Test
    void checkDuplicateId(){
        UserRegisterDto userRegisterDto = new UserRegisterDto("유저ID", "password", "nickname", "phone number", "abcd@ab.cd");
        userService.registerUser(userRegisterDto);
        User user = userJpaRepository.findAll().get(0);

        boolean b = userService.isDuplicateId("유저ID");

        assertThat(b).isEqualTo(true);

        userJpaRepository.delete(user);
    }

    @DisplayName("ID가 중복이 아닐 때 중복확인 테스트")
    @Test
    void checkNoDuplicateId(){
        UserRegisterDto userRegisterDto = new UserRegisterDto("유저ID", "password", "nickname", "phone number", "abcd@ab.cd");
        userService.registerUser(userRegisterDto);
        User user = userJpaRepository.findAll().get(0);

        boolean b = userService.isDuplicateId("유저ID2");

        assertThat(b).isEqualTo(false);

        userJpaRepository.delete(user);
    }

    @DisplayName("닉네임이 중복일 때 중복확인 테스트")
    @Test
    void checkDuplicateNickname(){
        UserRegisterDto userRegisterDto = new UserRegisterDto("유저ID", "password", "nickname", "phone number", "abcd@ab.cd");
        userService.registerUser(userRegisterDto);
        User user = userJpaRepository.findAll().get(0);

        boolean b = userService.isDuplicateNickname("nickname");

        assertThat(b).isEqualTo(true);

        userJpaRepository.delete(user);
    }

    @DisplayName("닉네임이 중복이 아닐 때 중복확인 테스트")
    @Test
    void checkNoDuplicateNickname(){
        UserRegisterDto userRegisterDto = new UserRegisterDto("유저ID", "password", "nickname", "phone number", "abcd@ab.cd");
        userService.registerUser(userRegisterDto);
        User user = userJpaRepository.findAll().get(0);

        boolean b = userService.isDuplicateNickname("nickname2");

        System.out.println(b);
        assertThat(b).isEqualTo(false);

        userJpaRepository.delete(user);
    }
}