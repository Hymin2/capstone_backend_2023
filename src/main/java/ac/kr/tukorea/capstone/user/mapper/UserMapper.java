package ac.kr.tukorea.capstone.user.mapper;

import ac.kr.tukorea.capstone.user.dto.UserRegisterDto;
import ac.kr.tukorea.capstone.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    public User UserRegisterInfo(UserRegisterDto userRegisterDto){
        return User.builder()
                .username(userRegisterDto.getUsername())
                .userPassword(userRegisterDto.getPassword())
                .nickname(userRegisterDto.getNickname())
                .phoneNumber(userRegisterDto.getPhoneNumber())
                .email(userRegisterDto.getEmail())
                .build();
    }

}
