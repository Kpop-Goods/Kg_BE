package hello.kpop.global.login.service;

import hello.kpop.user.User;
import hello.kpop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {

    private final UserRepository userRepository;
    private final int MAX_LOCK_ATTEMPTS = 5; // 최대 잠금 시도 횟수
    private final long LOCK_DURATION_MINUTES = 10; // 잠금 기간 (분)

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("해당 이메일이 존재하지 않습니다." + userEmail));

        if (user.getLockYn() == 'Y') {
            unlockAccount(user); // 잠금 해제 시도
        }

        if (user.getLockCnt() >= MAX_LOCK_ATTEMPTS && user.getLockYn() == 'N') {
            user.setLockYn('Y'); // 잠금 상태로 변경
            user.setLockLastDate(LocalDateTime.now()); // 잠금 최종 시점 설정
            userRepository.save(user); // 변경 사항 저장
            throw new LockedException("계정이 잠겼습니다. 잠금 시간이 " + LOCK_DURATION_MINUTES + "분 남아있습니다.");
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUserEmail())
                .password(user.getPassword())
                .roles(user.getUserType().name())
                .build();
    }

    // 잠금 해제 메서드
    public void unlockAccount(User user) {
        if (user.getLockYn() == 'Y') {
            LocalDateTime lockLastDate = user.getLockLastDate();
            LocalDateTime currentTime = LocalDateTime.now();
            long minutesElapsed = ChronoUnit.MINUTES.between(lockLastDate, currentTime);

            if (minutesElapsed >= LOCK_DURATION_MINUTES) {
                // 잠금 시간이 지난 경우
                user.updateLock('N', 0); // 잠금 횟수 초기화
                userRepository.save(user); // 변경 사항 저장
            } else {
                // 일정 시간이 지나지 않은 경우 처리할 내용 추가
                throw new LockedException("아직 잠금이 해제되지 않았습니다.");
            }
        }
    }
}