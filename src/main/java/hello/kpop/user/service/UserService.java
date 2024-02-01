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
        // 여기에 사용자 정보 확인하는 코드가 있었을 텐데 삭제하세요.
    }
}

