package hello.kpop.socialing.service;

import hello.kpop.socialing.common.ProcessUtils;
import hello.kpop.socialing.exception.UnAuthorizedException;
import hello.kpop.user.Role;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAuthentication {

    // user 로그인 상태와 타입 권한 체크
    public String auth(Authentication authentication){
        if (authentication == null) {
            // 인증되지 않은 사용자 처리
            throw new UnAuthorizedException(ProcessUtils.getMessage("Login.Error", "errors"));
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (ObjectUtils.isEmpty(userDetails) || userDetails.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(Role.GUEST.getKey()))) {
            // 로그아웃, GUEST 시 실행
            throw new UnAuthorizedException();
        }
        return userDetails.getUsername();
    }

    public boolean isAdmin(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(Role.ADMIN.getKey()));
    }


}


