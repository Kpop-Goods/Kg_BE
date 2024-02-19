package hello.kpop.socialing.service;

import hello.kpop.global.jwt.TokenValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final TokenValidator tokenValidator;

    public boolean isUserLoggedIn(String token) {
        // 토큰의 유효성을 확인하여 사용자의 로그인 상태를 확인
        if (tokenValidator.validateToken(token)) {
            // 토큰이 유효한 경우
            return true;
        } else {
            // 토큰이 유효하지 않은 경우
            return false;
        }
    }
    public String getUserType() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                if (authority.getAuthority().equals("ROLE_ADMIN")) {
                    return "관리자";
                } else if (authority.getAuthority().equals("ROLE_USER")) {
                    return "유저";
                }
            }
        }
        return "게스트";
    }
}
