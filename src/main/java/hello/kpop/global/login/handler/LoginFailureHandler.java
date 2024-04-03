package hello.kpop.global.login.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.kpop.user.User;
import hello.kpop.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("statusCode", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        String userEmail = (String) request.getAttribute("userEmail");

        User user = userRepository.findByUserEmail(userEmail)
                .orElse(null);

        if (user == null) {
            // userEmail이 없으면 에러 응답 전송
            responseBody.put("message", "해당 이메일이 존재하지 않습니다.");
            String jsonResponse = new ObjectMapper().writeValueAsString(responseBody);
            response.getWriter().write(jsonResponse);
            return;
        } else {
            if (user.getLockYn().equals('Y')) {
                LocalDateTime unlockTime = user.getLockLastDate();
                long remainingMinutes = calculateRemainingMinutes(unlockTime);

                String remainingTimeString;
                if (remainingMinutes <= 0) {
                    remainingTimeString = "지금 바로 시도해주세요.";
                } else {
                    remainingTimeString = remainingMinutes + "분 후에 다시 시도해주세요.";
                }

                responseBody.put("message", "잠금 횟수를 초과하여 계정이 잠겼습니다. " + remainingTimeString);
            } else {
                responseBody.put("message", "로그인 실패, 이메일이나 비밀번호를 확인해주세요.");
            }

            userRepository.incrementLockCount(userEmail); // 잠금 횟수 증가

            String jsonResponse = new ObjectMapper().writeValueAsString(responseBody);
            response.getWriter().write(jsonResponse);

            log.info("로그인에 실패했습니다. 메시지 : {}", exception.getMessage());
            log.error("로그인 실패 - 스택 트레이스: ", exception);
        }
    }

    private long calculateRemainingMinutes(LocalDateTime unlockTime) {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime unlockTimePlus10Minutes = unlockTime.plusMinutes(11); // 10분 후의 시간 계산
        long remainingMinutes = currentTime.until(unlockTimePlus10Minutes, ChronoUnit.MINUTES);
        return remainingMinutes > 0 ? remainingMinutes : 0;
    }

}