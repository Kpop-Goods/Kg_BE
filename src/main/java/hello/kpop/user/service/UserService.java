package hello.kpop.user.service;

import hello.kpop.user.Role;
import hello.kpop.user.User;
import hello.kpop.user.dto.UserRequestDto;
import hello.kpop.user.dto.UserResponseDto;
import hello.kpop.user.dto.UserSuccessResponseDto;
import hello.kpop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate<String, String> redisTemplate;

    @Transactional
    public UserResponseDto signUp(UserRequestDto userRequestDto) throws Exception {

        if (userRepository.findByUserEmail(userRequestDto.getUserEmail()).isPresent()) {
            throw new Exception("이미 존재하는 이메일입니다.");
        }

        if (userRepository.findByNickname(userRequestDto.getNickname()).isPresent()) {
            throw new Exception("이미 존재하는 닉네임입니다.");
        }

        User user = User.builder()
                .userName(userRequestDto.getUserName())
                .userEmail(userRequestDto.getUserEmail())
                .password(userRequestDto.getPassword())
                .nickname(userRequestDto.getNickname())
                .userType(Role.USER)
                .build();

        user.passwordEncode(passwordEncoder);
        userRepository.save(user);
        return new UserResponseDto(user);
    }

    //유저 회원정보 수정
    @Transactional
    public UserResponseDto updateUser(Long id, UserRequestDto userRequestDto) throws Exception {
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다."));

        // // 사용자가 입력한 비밀번호를 암호화하여 저장된 비밀번호와 비교합니다.
        // if (!passwordEncoder.matches(userRequestDto.getUserPw(), user.getUserPw())) {
        //     throw new Exception("비밀번호가 일치하지 않습니다.");
        // }

        user.update(userRequestDto,passwordEncoder);
        return new UserResponseDto(user);
    }

    //유저 회원 탈퇴
    @Transactional
    public UserSuccessResponseDto deleteUser(Long id) throws Exception {
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다."));

        userRepository.deleteById(id);
        return new UserSuccessResponseDto(true);
    }

    // 로그아웃
    @Transactional
    public void logout(Authentication authentication) {
        String email = authentication.getName();
        System.out.println("이메일: " + email);

        if (redisTemplate.hasKey(email)) {
            redisTemplate.delete(email); // 해당 키가 존재하면 삭제
            System.out.println("토큰 삭제됨");
        } else {
            System.out.println("토큰이 존재하지 않음");
        }
    }

    // 비밀번호 재설정 메서드
    @Transactional
    public void resetPassword(String email, String token, String newPassword) throws Exception {
        // 이메일과 토큰을 사용하여 사용자를 확인
        User user = userRepository.findByUserEmail(email)
                .orElseThrow(() -> new Exception("사용자를 찾을 수 없습니다."));

        // 토큰이 맞는지 확인
        String storedEmail = redisTemplate.opsForValue().get(token);
        if (storedEmail == null || !storedEmail.equals(email)) {
            throw new Exception("유효하지 않은 토큰입니다.");
        }

        // 새로운 비밀번호와 이전 비밀번호가 다른지 확인
        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new Exception("새로운 비밀번호는 이전 비밀번호와 다르게 설정해야 합니다.");
        }

        // 새로운 비밀번호로 암호화하여 저장
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user);

        // 업데이트 후에는 사용된 토큰은 삭제
        redisTemplate.delete(token);
    }
}

