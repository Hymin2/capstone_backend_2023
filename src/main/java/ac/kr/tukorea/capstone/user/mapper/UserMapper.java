package ac.kr.tukorea.capstone.user.mapper;

import ac.kr.tukorea.capstone.user.dto.UserRegisterDto;
import ac.kr.tukorea.capstone.user.entity.Authority;
import ac.kr.tukorea.capstone.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class UserMapper {
    public User UserRegisterInfo(UserRegisterDto userRegisterDto){
        Authority authority = Authority.builder()
                .name("ROLE_USER")
                .build();

        return User.builder()
                .userName(userRegisterDto.getUserName())
                .userPassword(userRegisterDto.getPassword())
                .nickname(userRegisterDto.getNickname())
                .phoneNumber(userRegisterDto.getPhoneNumber())
                .email(userRegisterDto.getEmail())
                .authorities(Collections.singletonList(authority))
                .build();
    }
}
