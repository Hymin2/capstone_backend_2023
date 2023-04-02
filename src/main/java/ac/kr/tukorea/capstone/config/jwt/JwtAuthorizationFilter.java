package ac.kr.tukorea.capstone.config.jwt;

import ac.kr.tukorea.capstone.config.Exception.AccessTokenExpiredException;
import ac.kr.tukorea.capstone.config.Exception.InvalidAccessTokenException;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtTokenService jwtTokenService;
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwtToken = jwtTokenService.getJwtToken(request);

        if(!request.getRequestURI().startsWith("/api/v1/user/refresh") && jwtToken != null){
            try {
                jwtTokenService.validateAccessToken(jwtToken);

                log.info("jwt token 유효");

                Authentication auth = jwtTokenService.getAuthentication(jwtToken);
                SecurityContextHolder.getContext().setAuthentication(auth);

                filterChain.doFilter(request, response);
            }catch (InvalidAccessTokenException e){
                sendResponseMessage(response, 401, "Access token is invalid", "failed");
            }catch (AccessTokenExpiredException e){
                sendResponseMessage(response, 401, "Access token is expired", "failed");
            }
        }
    }

    private void sendResponseMessage(HttpServletResponse response, int status, String message, String result) throws IOException{
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", status);
        jsonObject.put("message", message);
        jsonObject.put("result", result);

        response.getWriter().print(jsonObject);
        response.getWriter().flush();
    }
}
