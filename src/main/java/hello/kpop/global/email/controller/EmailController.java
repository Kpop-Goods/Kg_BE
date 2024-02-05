package hello.kpop.global.email.controller;

import hello.kpop.global.email.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send-verification-email")
    public ResponseEntity<String> sendVerificationEmail(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");

        try {
            // 백엔드에서 이메일 전송 기능 호출
            emailService.sendVerificationEmail(email);
            return ResponseEntity.ok("이메일이 성공적으로 전송되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이메일 전송에 실패했습니다.");
        }
    }

    @PostMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        String verificationCode = payload.get("verificationCode");

        try {
            // 백엔드에서 이메일 인증 처리 기능 호출
            boolean isVerified = emailService.verifyEmail(email, verificationCode);

            if (isVerified) {
                return ResponseEntity.ok("이메일이 성공적으로 인증되었습니다.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증에 실패했습니다. 올바른 인증 코드를 입력하세요.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("인증 처리 중에 오류가 발생했습니다.");
        }
    }
}
