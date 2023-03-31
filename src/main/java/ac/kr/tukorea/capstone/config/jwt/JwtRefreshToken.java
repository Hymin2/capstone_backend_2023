package ac.kr.tukorea.capstone.config.jwt;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Id;

@RequiredArgsConstructor
@Getter
public class JwtRefreshToken {
    @Id
    private final String refreshToken;
    private final String username;
}
