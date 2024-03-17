package hello.kpop.socialing.common;


import hello.kpop.user.Role;
import hello.kpop.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
@RequiredArgsConstructor
public class SocialingUtils {

    private final HttpSession session;
    private final HttpServletRequest request;

    public boolean isAdmin(){
        if (isLogin()) {
            return getUser().getUserType() == Role.ADMIN;
        }
        return false;
    }

    public boolean isLogin() {

        return getUser() != null;
    }

    public User getUser() {
        return (User) session.getAttribute("user");
    }


    /**
     * 비회원 ID(ID)
     *          IP + 브라우저 정보
     *
     * @return
     */
    public int guestId() {
        String ip = request.getRemoteAddr();
        String ua = request.getHeader("User-Agent");

        return Objects.hash(ip, ua);
    }


}
