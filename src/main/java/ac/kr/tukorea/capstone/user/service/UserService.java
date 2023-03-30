package ac.kr.tukorea.capstone.user.service;

import ac.kr.tukorea.capstone.config.WebSecurityConfig;
import ac.kr.tukorea.capstone.config.auth.UserDetailsImpl;
import ac.kr.tukorea.capstone.config.auth.UserDetailsImplService;
import ac.kr.tukorea.capstone.user.dto.UserLoginDto;
import ac.kr.tukorea.capstone.user.dto.UserRegisterDto;
import ac.kr.tukorea.capstone.user.entity.Authority;
import ac.kr.tukorea.capstone.user.entity.User;
import ac.kr.tukorea.capstone.user.mapper.UserMapper;
import ac.kr.tukorea.capstone.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserJpaRepository userJpaRepository;
    private final WebSecurityConfig webSecurityConfig;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;

    public User registerUser(UserRegisterDto userRegisterDto){
        try {
            User user = userMapper.UserRegisterInfo(userRegisterDto);
            user.setEncodePassword(webSecurityConfig.getPasswordEncoder().encode(user.getUserPassword()));

            Authority authority = Authority.builder()
                    .name("ROLE_USER")
                    .user(user)
                    .build();

            user.setAuthorities(Collections.singletonList(authority));

            return userJpaRepository.save(user);
        }catch (RuntimeException e){
            return null;
        }
    }

    public Boolean isDuplicateId(String username){
        try{
            boolean b = userJpaRepository.existsByUsername(username);
            return b;
        }catch (RuntimeException e){
            return null;
        }
    }

    public boolean isDuplicateNickname(String nickname){
        return userJpaRepository.existsByNickname(nickname);
    }

    public void deleteUser(String username){
        userJpaRepository.deleteByUsername(username);
    }

}
