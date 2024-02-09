package hello.kpop.global.email.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Map;
import java.util.HashMap;

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
}