package hello.kpop.socialing.common;

import hello.kpop.socialing.common.exception.UnAuthorizedException;
import hello.kpop.user.Role;
import hello.kpop.user.User;
import hello.kpop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class UserAuthentication {

    private final UserRepository userRepository;

    // user 로그인 상태와 타입 권한 체크
    public String auth(Authentication authentication) {
        if (authentication == null) {
            // 인증되지 않은 사용자 처리
            throw new UnAuthorizedException();
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (ObjectUtils.isEmpty(userDetails) || userDetails.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(Role.GUEST.getKey()))) {
            // 로그아웃, GUEST 시 실행
            throw new UnAuthorizedException(ProcessUtils.getMessage("Login.Error", "errors"));
        }
        return userDetails.getUsername();
    }

    //인증과 로그인이 된 유저
    public User getUser(Authentication authentication) {
        String email = auth(authentication);
        User user = userRepository.findByUserEmail(email).orElseThrow(null);
        return user;
    }


    //등록한 유저 삭제 및 수정 권한 체크
    public void checkUser(String email, Authentication authentication) {
        String authEmail = auth(authentication);
        if (!StringUtils.hasText(authEmail) || !authEmail.equals(email)) {
            throw new UnAuthorizedException(ProcessUtils.getMessage("Not.your", "errors"));
        }
    }

    //인증과 로그인이 된 유저 관리자 체크
    public boolean checkAdmin(Authentication authentication) {
        try {
            String role = getUser(authentication).getUserType().getKey();
            if (role.equals(Role.ADMIN.getKey())) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}


