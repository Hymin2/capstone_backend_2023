package ac.kr.tukorea.capstone.config.auth;


import ac.kr.tukorea.capstone.user.entity.User;
import ac.kr.tukorea.capstone.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class UserDetailsImplService implements UserDetailsService {
    private final UserRepository userRepository;
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        Supplier<UsernameNotFoundException> s =
                () -> new UsernameNotFoundException(
                        "invalid id");

        log.info("---------------------------------------------------");
        log.info("UserDetailsImplService Start");
        log.info("로그인 시도한 username이 등록된 사용자인지 확인");
        log.info("username: " + username);
        log.info("UserDetailsImplService End");
        log.info("---------------------------------------------------");

        User user = userRepository
                .findByUsername(username).orElseThrow(s);

        return new UserDetailsImpl(user);
    }
}
