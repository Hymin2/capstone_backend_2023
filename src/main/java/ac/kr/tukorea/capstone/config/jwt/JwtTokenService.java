package ac.kr.tukorea.capstone.config.jwt;


import ac.kr.tukorea.capstone.config.auth.UserDetailsImpl;
import ac.kr.tukorea.capstone.config.auth.UserDetailsImplService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

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
        UserDetailsImpl userDetails = userDetailsImplService.loadUserByUsername(getUsername(jwtToken));

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public boolean validateAccessToken(String jwtToken){
        try{
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(jwtToken);
            return claims.getBody().getExpiration().after(new Date());
        }catch (Exception e){
            return false;
        }
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

    public String createRefreshToken(String username){
        String refreshToken = Jwts.builder()
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .setIssuedAt(new Date())
                .compact();

        JwtRefreshToken jwtRefreshToken = new JwtRefreshToken(refreshToken, username);
        jwtRefreshTokenRepository.save(jwtRefreshToken);

        return refreshToken;
    }


}
