package ac.kr.tukorea.capstone.config.auth;

import ac.kr.tukorea.capstone.user.entity.User;
import ac.kr.tukorea.capstone.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class UserDetailsImplService implements UserDetailsService {
    private final UserJpaRepository userJpaRepository;

    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        Supplier<UsernameNotFoundException> s =
                () -> new UsernameNotFoundException(
                        "invalid id or password");
        User user = userJpaRepository
                .findByUsername(username)
                .orElseThrow(s);

        return new UserDetailsImpl(user);
    }

}
