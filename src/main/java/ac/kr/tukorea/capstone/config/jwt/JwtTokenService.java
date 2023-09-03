package ac.kr.tukorea.capstone.config.jwt;


import ac.kr.tukorea.capstone.config.Exception.AccessTokenExpiredException;
import ac.kr.tukorea.capstone.config.Exception.InvalidAccessTokenException;
import ac.kr.tukorea.capstone.config.Exception.InvalidRefreshTokenException;
import ac.kr.tukorea.capstone.config.Exception.RefreshTokenExpiredException;
import ac.kr.tukorea.capstone.config.auth.UserDetailsImpl;
import ac.kr.tukorea.capstone.config.auth.UserDetailsImplService;
import ac.kr.tukorea.capstone.user.entity.User;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class JwtTokenService {
    @Value("${jwt.secret}")
    private String secretKey;

    private final long ACCESS_VALID_SECOND = 1000L * 60 * 60 * 24 * 30;
    private final UserDetailsImplService userDetailsImplService;
    private final JwtRefreshTokenRepository jwtRefreshTokenRepository;
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private Key getSecretKey(){
        byte[] KeyBytes = secretKey.getBytes(StandardCharsets.UTF_8);

        return Keys.hmacShaKeyFor(KeyBytes);
    }

    public Authentication getAuthentication(String token){
        log.info("Access Token 으로 User 정보 불러오기");
        Claims claims = parseClaims(token).getBody();

        Collection<? extends GrantedAuthority> authorities = Arrays
                .stream(claims.get("auth")
                .toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

         UserDetailsImpl userDetails = new UserDetailsImpl(User
                         .builder()
                         .username(claims.getSubject())
                            .build());
        log.info("username: " + userDetails.getUsername() + " ");
        log.info("Authority: " + authorities);
        log.info("jwt body: " + claims.get("auth"));

        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

    public String getUsername(String accessToken){
        Claims claims = parseClaims(accessToken).getBody();

        UserDetailsImpl userDetails = new UserDetailsImpl(User
                .builder()
                .username(claims.getSubject())
                .build());

        return userDetails.getUsername();
    }

    public String getJwtToken(String token){
        if(Strings.hasLength(token) && token.startsWith("Bearer "))
            return token.substring(7);

        return null;
    }

    public Jws<Claims> parseClaims(String token){
        try {
            Jws<Claims> claims = Jwts
                    .parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token);
            return claims;
        }catch (ExpiredJwtException e){
            log.info("유효 기간이 지난 Access Token");
            throw new AccessTokenExpiredException();
        }catch (Exception e){
            log.info("기타 유효하지 않은 Access Token");
            throw new InvalidAccessTokenException();
        }
    }

    public boolean validateAccessToken(String accessToken){
        return parseClaims(accessToken) != null;
    }

    public void validateRefreshToken(String refreshToken, String username){
        JwtRefreshToken jwtRefreshToken = jwtRefreshTokenRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new RefreshTokenExpiredException());

        if(!jwtRefreshToken.getUsername().equals(username)) throw new InvalidRefreshTokenException();

        log.info("유효한 Refresh Token");
    }

    public String createAccessToken(UserDetailsImpl userDetails){
        Date now = new Date();

        log.info("create jwt authority: ", userDetails.getAuthorities());

        String authorities = userDetails
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("auth", authorities)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ACCESS_VALID_SECOND))
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String refreshAccessToken(HttpServletRequest request, String username){
        String refreshToken = getJwtToken(request.getHeader("Authorization"));
        validateRefreshToken(refreshToken, username);

        return createAccessToken(userDetailsImplService.loadUserByUsername(username));
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
