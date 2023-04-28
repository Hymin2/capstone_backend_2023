package ac.kr.tukorea.capstone.user.service;

import ac.kr.tukorea.capstone.config.auth.UserDetailsImpl;
import ac.kr.tukorea.capstone.user.dto.UserRegisterDto;
import ac.kr.tukorea.capstone.user.entity.User;
import ac.kr.tukorea.capstone.user.repository.UserJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
        UserRegisterDto userRegisterDto = new UserRegisterDto("유저ID1", "password", "nickname1", "phone number", "abcd@ab.cd");

        userService.registerUser(userRegisterDto);

        User user2 = userJpaRepository.findByUsername("유저ID1").get();

        assertThat(user2.getUsername()).isEqualTo(userRegisterDto.getUsername());
        assertThat(user2.getNickname()).isEqualTo(userRegisterDto.getNickname());
        assertThat(user2.getEmail()).isEqualTo(userRegisterDto.getEmail());
        assertThat(user2.getPhoneNumber()).isEqualTo(userRegisterDto.getPhoneNumber());

        userJpaRepository.delete(user2);
    }

    @DisplayName("패스워드가 암호화되서 저장되는지 테스트")
    @Test
    void saveEncodedPassword(){
        UserRegisterDto userRegisterDto = new UserRegisterDto("유저ID2", "password", "nickname2", "phone number", "abcd@ab.cd");
        userService.registerUser(userRegisterDto);

        User user = userJpaRepository.findByUsername("유저ID2").get();

        assertThat("password").isNotEqualTo(user.getUserPassword());

        userJpaRepository.delete(user);
    }

    @DisplayName("ID가 중복일 때 중복확인 테스트")
    @Test
    void checkDuplicateId(){
        UserRegisterDto userRegisterDto = new UserRegisterDto("유저ID3", "password", "nickname3", "phone number", "abcd@ab.cd");
        userService.registerUser(userRegisterDto);
        User user = userJpaRepository.findByUsername("유저ID3").get();

        boolean b = userService.isDuplicateId("유저ID3");

        assertThat(b).isEqualTo(true);

        userJpaRepository.delete(user);
    }

    @DisplayName("ID가 중복이 아닐 때 중복확인 테스트")
    @Test
    void checkNoDuplicateId(){
        UserRegisterDto userRegisterDto = new UserRegisterDto("유저ID4", "password", "nickname4", "phone number", "abcd@ab.cd");
        userService.registerUser(userRegisterDto);
        User user = userJpaRepository.findByUsername("유저ID4").get();

        boolean b = userService.isDuplicateId("유저ID2");

        assertThat(b).isEqualTo(false);

        userJpaRepository.delete(user);
    }

    @DisplayName("닉네임이 중복일 때 중복확인 테스트")
    @Test
    void checkDuplicateNickname(){
        UserRegisterDto userRegisterDto = new UserRegisterDto("유저5", "password", "nickname5", "phone number", "abcd@ab.cd");

        userService.registerUser(userRegisterDto);
        User user = userJpaRepository.findByUsername("유저5").get();

        boolean b = userService.isDuplicateNickname("nickname5");

        assertThat(b).isEqualTo(true);

        userJpaRepository.delete(user);
    }

    @DisplayName("닉네임이 중복이 아닐 때 중복확인 테스트")
    @Test
    void checkNoDuplicateNickname(){
        UserRegisterDto userRegisterDto = new UserRegisterDto("유저6", "password", "nickname6", "phone number", "abcd@ab.cd");
        userService.registerUser(userRegisterDto);
        User user = userJpaRepository.findByUsername("유저6").get();

        boolean b = userService.isDuplicateNickname("nickname2");

        System.out.println(b);
        assertThat(b).isEqualTo(false);

        userJpaRepository.delete(user);
    }

    @DisplayName("UserDetails authorities 출력 테스트")
    @Test
    void printUserDetailAuthorities(){
        User user = userJpaRepository.findByUsername("user12").get();
        UserDetailsImpl userDetails = new UserDetailsImpl(user);

        userDetails.getAuthorities().stream().forEach(System.out::println);

        System.out.println(userDetails.getAuthorities());

        List<String> list = userDetails.getAuthorities().stream().map(String::valueOf).collect(Collectors.toList());
    }
}