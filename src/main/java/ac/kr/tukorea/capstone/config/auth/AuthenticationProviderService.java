package ac.kr.tukorea.capstone.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationProviderService implements AuthenticationProvider {
    private final UserDetailsImplService userDetailsImplService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        System.out.println(username +" " + password);

        UserDetailsImpl user = userDetailsImplService.loadUserByUsername(username);

        return checkPassword(user, password);
    }

    private Authentication checkPassword(UserDetailsImpl user, String rawPassword) {
        if(passwordEncoder.matches(rawPassword, user.getPassword())){
            return new UsernamePasswordAuthenticationToken(
                    user,
                    user.getPassword(),
                    user.getAuthorities()
            );
        }

        else{
            throw new BadCredentialsException("invalid id or password");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
