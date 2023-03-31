package ac.kr.tukorea.capstone.config.auth;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationProviderService implements AuthenticationProvider {
    private final UserDetailsImplService userDetailsImplService;
    private final PasswordEncoder passwordEncoder;
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        log.info("---------------------------------------------------");
        log.info("AuthenticationProviderService Start");
        log.info("1. 로그인을 시도한 username이 등록되어 있는지 확인");
        log.info("username: " + username);
        log.info("password: " + password);

        UserDetailsImpl user = userDetailsImplService.loadUserByUsername(username);

        return checkPassword(user, password);
    }

    private Authentication checkPassword(UserDetailsImpl user, String rawPassword) {
        log.info("2. 로그인을 시도한 username의 비밀번호가 일치하는지 확인");
        if(passwordEncoder.matches(rawPassword, user.getPassword())){
            log.info("비밀번호 일치");
            log.info("AuthenticationProviderService End");
            log.info("---------------------------------------------------");
            return new UsernamePasswordAuthenticationToken(
                    user,
                    user.getPassword(),
                    user.getAuthorities()
            );
        }

        else{
            log.info("비밀번호 불일치");
            log.info("AuthenticationProviderService End");
            log.info("---------------------------------------------------");

            throw new BadCredentialsException("invalid id or password");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
