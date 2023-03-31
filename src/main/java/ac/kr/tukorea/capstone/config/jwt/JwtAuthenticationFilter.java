package ac.kr.tukorea.capstone.config.jwt;

import ac.kr.tukorea.capstone.config.auth.UserDetailsImpl;
import ac.kr.tukorea.capstone.user.dto.UserLoginDto;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenProvider;
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());


    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenService jwtTokenProvider){
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;

        setFilterProcessesUrl("/api/v1/user/login");
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
        throws AuthenticationException{
        ObjectMapper objectMapper = new ObjectMapper();
        UserLoginDto userLoginDto;

        try{
            userLoginDto = objectMapper.readValue(request.getInputStream(), UserLoginDto.class);
        } catch (StreamReadException e) {
            throw new RuntimeException(e);
        } catch (DatabindException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userLoginDto.getUsername(), userLoginDto.getPassword());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        log.info("---------------------------------------------------");
        log.info("JwtAuthenticationFilter Start");

        return authentication;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response
            , FilterChain filterChain, Authentication authentication) throws IOException {

        UserDetailsImpl userDetails = (UserDetailsImpl)authentication.getPrincipal();

        String accessToken = jwtTokenProvider.createAccessToken(userDetails.getUsername());
        String refreshToken = jwtTokenProvider.createRefreshToken(userDetails.getUsername());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.addHeader("Authorization", "Bearer " + accessToken);
        response.addHeader("Authorization-refresh", "Bearer " + refreshToken);

        response.getWriter().write("{\n" + "\t\"status\": 200,\n" + "\t\"message\": \"Login success\",\n" + "\t\"result\": \"success\",\n" + "\t\"access_token\": \"" + accessToken + "\"\n}");
        response.getWriter().flush();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("{\n" + "\t\"status\": 401,\n" + "\t\"message\": \"Invalid id or password\",\n" + "\t\"result\": \"failed\"\n" + "}");
        response.getWriter().flush();
    }
}
