package ac.kr.tukorea.capstone.user.service;

import ac.kr.tukorea.capstone.config.Security.WebSecurityConfig;
import ac.kr.tukorea.capstone.user.dto.UserRegisterDto;
import ac.kr.tukorea.capstone.user.entity.User;
import ac.kr.tukorea.capstone.user.mapper.UserMapper;
import ac.kr.tukorea.capstone.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserJpaRepository userJpaRepository;
    private final WebSecurityConfig webSecurityConfig;
    private final UserMapper userMapper;

    public User registerUser(UserRegisterDto userRegisterDto){
        User user = userMapper.UserRegisterInfo(userRegisterDto);
        user.setEncodePassword(webSecurityConfig.getPasswordEncoder().encode(user.getUserPassword()));

        return userJpaRepository.save(user);
    }

    public boolean isDuplicateId(String username){
        return userJpaRepository.existsByUserName(username);
    }

    public boolean isDuplicateNickname(String nickname){
        return userJpaRepository.existsByNickname(nickname);
    }

    public void deleteUser(String username){
        userJpaRepository.deleteByUserName(username);
    }
}
