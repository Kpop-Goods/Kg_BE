package hello.kpop.global.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenValidator {

    private static final String SECRET_KEY = "dasdasf234fuhvertsv34789yhiuFDSIUFGYDTE5rcomewithme5456empowerjourneys";

    private final RedisTemplate<String, String> redisTemplate;

    public TokenValidator(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            // 토큰의 만료 기간 확인
            if (claims.getExpiration().before(new Date())) {
                return false; // 만료된 토큰
            }

            // Redis에 토큰이 저장되어 있는지 확인
            String email = claims.getSubject();
            if (!redisTemplate.hasKey(email)) {
                return false; // Redis에 토큰이 없는 경우
            }

            // 검증 로직 (추가할 땐 여기로)

            return true; // 유효한 토큰
        } catch (Exception e) {
            // 토큰 검증 실패
            return false;
        }
    }
}