package ac.kr.tukorea.capstone.config.auth.jwt;


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
public class JwtTokenProvider {
    @Value("$[jwt.secret]")
    private String secretKey;

    private final long VALID_MILISECOND = 1000L * 60 * 30;
    private final UserDetailsImplService userDetailsImplService;

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

        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
    }
    public boolean validateToken(String jwtToken){
        try{
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(jwtToken);
            return claims.getBody().getExpiration().before(new Date());

        }catch (Exception e){
            return false;
        }
    }

    public String createJwtToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + VALID_MILISECOND))
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
