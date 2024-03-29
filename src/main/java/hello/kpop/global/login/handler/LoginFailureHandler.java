package hello.kpop.global.login.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("statusCode", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        responseBody.put("message", "로그인 실패, 이메일이나 비밀번호를 확인해주세요.");

        response.getWriter().write(new ObjectMapper().writeValueAsString(responseBody));
        log.info("로그인에 실패했습니다. 메시지 : {}", exception.getMessage());
        log.error("로그인 실패 - 스택 트레이스: ", exception);
    }
}
