package hello.kpop.global.email.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private RedisTemplate<String, String> redisTemplate; // RedisTemplate 주입

    @Value("${app.base-url}")
    private String baseUrl;

    private static final String VERIFICATION_CODES_KEY = "emailVerificationCodes";

    public void sendVerificationEmail(String toEmail) throws MessagingException {
        String subject = "이메일 인증";
        String token = generateVerificationToken();
        String text = "이메일 인증을 위해선 다음 코드를 입력해 주세요. : " + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(text);

        javaMailSender.send(message);

        // 생성된 토큰은 일정 시간 동안 유효하도록 Redis에 저장
        redisTemplate.opsForHash().put(VERIFICATION_CODES_KEY, toEmail, token);
    }

    public boolean verifyEmail(String email, String verificationCode) {
        String storedCode = (String) redisTemplate.opsForHash().get(VERIFICATION_CODES_KEY, email);

        if (storedCode != null && storedCode.equals(verificationCode)) {
            // 인증 성공 시 해당 이메일의 코드는 삭제하여 중복 사용 방지
            redisTemplate.opsForHash().delete(VERIFICATION_CODES_KEY, email);
            return true;
        }

        return false;
    }

    private String generateVerificationToken() {
        // 인증번호 생성 로직 구현 (랜덤 문자열 생성)
        return RandomStringUtils.randomAlphanumeric(6); // 예시로 6자리 랜덤 문자열 생성
    }

    // 비밀번호 재설정 토큰을 생성하고 이메일로 전송하는 메서드
    public void sendPasswordResetEmail(String toEmail) throws MessagingException {
        String subject = "비밀번호 재설정";
        String token = generatePasswordResetToken();

        // 이메일에 비밀번호 재설정 링크 포함
        String text = "비밀번호를 재설정하려면 다음 링크를 클릭하세요: " + baseUrl + "/reset-password?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(text);

        javaMailSender.send(message);

        // 생성된 토큰을 Redis에 저장하고 만료 시간 설정
        redisTemplate.opsForValue().set(token, toEmail, 15, TimeUnit.MINUTES);
    }

    // 비밀번호 재설정 토큰 생성 메서드
    private String generatePasswordResetToken() {
        return RandomStringUtils.randomAlphanumeric(20); // 예시로 20자리 랜덤 문자열 생성
    }
}