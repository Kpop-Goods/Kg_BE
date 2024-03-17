package hello.kpop.global.login.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.kpop.global.jwt.service.JwtService;
import hello.kpop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    /* 커스텀 JSON 로그인 필터를 정상적으로 통과하여 인증 처리가 되었을 때(=로그인 성공)
     * 로그인 성공 핸들러가 동작한다.
     * SimpleUrlAuthenticationSuccessHandler를 상속받아 구현했다.
     */

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RedisTemplate<String, String> redisTemplate;

    @Value("${jwt.access.expiration}")
    private String accessTokenExpiration;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        String email = extractUsername(authentication);
        String accessToken = jwtService.createAccessToken(email);
        String refreshToken = jwtService.createRefreshToken();

        if (email != null && accessToken != null) {
            redisTemplate.opsForValue().set(email, accessToken);
        } else {
            log.error("Email 또는 AccessToken 값이 null입니다. Redis에 저장하지 않습니다.");
            return;
        }

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("statusCode", 200);
        responseBody.put("message", "로그인 성공");

        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(responseBody));

        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);

        userRepository.findByUserEmail(email)
                .ifPresent(user -> {
                    user.updateRefreshToken(refreshToken);
                    userRepository.saveAndFlush(user);
                });
        log.info("로그인에 성공했습니다. 이메일 : {}", email);
        log.info("로그인에 성공했습니다. AccessToken : {}", accessToken);
        log.info("발급된 AccessToken 만료 기간 : {}", accessTokenExpiration);
    }

    private String extractUsername(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();

    }
}
