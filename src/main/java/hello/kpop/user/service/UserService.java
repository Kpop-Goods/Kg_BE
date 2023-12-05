package hello.kpop.user.service;

import hello.kpop.user.Role;
import hello.kpop.user.User;
import hello.kpop.user.dto.UserSignUpDto;
import hello.kpop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입 시, 유효성 체크
    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();

        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }

        return validatorResult;
    }

    public void signUp(UserSignUpDto userSignUpDto) throws Exception {

        if (userRepository.findByUserEmail(userSignUpDto.getUserEmail()).isPresent()) {
            throw new Exception("이미 존재하는 이메일입니다.");
        }

        if (userRepository.findByUserNickname(userSignUpDto.getUserNickname()).isPresent()) {
            throw new Exception("이미 존재하는 닉네임입니다.");
        }

        User user = User.builder()
                .userName(userSignUpDto.getUserName())
                .userEmail(userSignUpDto.getUserEmail())
                .userPw(userSignUpDto.getUserPw())
                .userNickname(userSignUpDto.getUserNickname())
                .role(Role.USER)
                .build();

        user.passwordEncode(passwordEncoder);
        userRepository.save(user);
    }
}

