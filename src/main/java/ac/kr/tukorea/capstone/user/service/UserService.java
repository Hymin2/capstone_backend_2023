package ac.kr.tukorea.capstone.user.service;

import ac.kr.tukorea.capstone.config.WebSecurityConfig;
import ac.kr.tukorea.capstone.user.dto.UserRegisterDto;
import ac.kr.tukorea.capstone.user.entity.Authority;
import ac.kr.tukorea.capstone.user.entity.User;
import ac.kr.tukorea.capstone.user.mapper.UserMapper;
import ac.kr.tukorea.capstone.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final WebSecurityConfig webSecurityConfig;
    private final UserMapper userMapper;

    public User registerUser(UserRegisterDto userRegisterDto){
        User user = userMapper.UserRegisterInfo(userRegisterDto);
        user.setEncodePassword(webSecurityConfig.getPasswordEncoder().encode(user.getUserPassword()));

        Authority authority = Authority.builder()
                .name("ROLE_USER")
                .user(user)
                .build();

        user.setAuthorities(Collections.singletonList(authority));

        return userRepository.save(user);
    }

    public Boolean isDuplicateId(String username){
        return userRepository.existsByUsername(username);
    }

    public boolean isDuplicateNickname(String nickname){
        return userRepository.existsByNickname(nickname);
    }

    public void deleteUser(String username){
        userRepository.deleteByUsername(username);
    }

}
