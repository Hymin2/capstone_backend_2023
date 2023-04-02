package ac.kr.tukorea.capstone.config.jwt;


import ac.kr.tukorea.capstone.config.Exception.AccessTokenExpiredException;
import ac.kr.tukorea.capstone.config.Exception.InvalidAccessTokenException;
import ac.kr.tukorea.capstone.config.Exception.InvalidRefreshTokenException;
import ac.kr.tukorea.capstone.config.Exception.RefreshTokenExpiredException;
import ac.kr.tukorea.capstone.config.auth.UserDetailsImpl;
import ac.kr.tukorea.capstone.config.auth.UserDetailsImplService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.lang.Strings;
import io.jsonwebtoken.security.Keys;
import io.lettuce.core.RedisException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtTokenService {
    @Value("${jwt.secret}")
    private String secretKey;

    private final long ACCESS_VALID_SECOND = 1000L * 60 * 30;
    private final UserDetailsImplService userDetailsImplService;
    private final JwtRefreshTokenRepository jwtRefreshTokenRepository;
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private Key getSecretKey(){
        byte[] KeyBytes = secretKey.getBytes(StandardCharsets.UTF_8);

        return Keys.hmacShaKeyFor(KeyBytes);
    }

    private String getUsername(String jwtToken){
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody()
                .getSubject();
    }

    public Authentication getAuthentication(String jwtToken){
        log.info("User 정보 불러오기");
        UserDetailsImpl userDetails = userDetailsImplService.loadUserByUsername(getUsername(jwtToken));

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getJwtToken(HttpServletRequest request){
        String header = request.getHeader("Authorization");

        if(Strings.hasLength(header) && header.startsWith("Bearer "))
            return header.substring(7);

        return null;
    }

    public void validateAccessToken(String jwtToken){
        try{
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(jwtToken);

            if(claims.getBody().getExpiration().before(new Date())) throw new AccessTokenExpiredException();

            log.info("유효한 Access Token");
        }catch (Exception e){
            throw new InvalidAccessTokenException();
        }
    }

    public void validateRefreshToken(String refreshToken, String username){
        JwtRefreshToken jwtRefreshToken = jwtRefreshTokenRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new RefreshTokenExpiredException());

        if(!jwtRefreshToken.getUsername().equals(username)) throw new InvalidRefreshTokenException();

        log.info("유효한 Refresh Token");
    }

    public String createAccessToken(String username){
        Date now = new Date();

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ACCESS_VALID_SECOND))
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String refreshAccessToken(HttpServletRequest request, String username){
        String refreshToken = getJwtToken(request);
        validateRefreshToken(refreshToken, username);

        return createAccessToken(username);
    }

    public String createRefreshToken(String username) throws RedisException {
        String refreshToken = Jwts.builder()
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .setIssuedAt(new Date())
                .compact();

        JwtRefreshToken jwtRefreshToken = new JwtRefreshToken(refreshToken, username);
        jwtRefreshTokenRepository.save(jwtRefreshToken);

        return refreshToken;
    }

    public void deleteRefreshToken(String refreshToken){
        jwtRefreshTokenRepository.deleteByRefreshToken(refreshToken);
    }
}
