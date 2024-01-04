package hello.kpop.user;

import hello.kpop.user.dto.UserRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USER")
public class User {

    //회원 번호
    @Id //pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long userId;

    //회원 이름
    @Column
    private String userName;

    //회원 이메일
    @Column
    private String userEmail;

    //회원 비밀번호
    @Column
    private String userPw;

    //회원 닉네임
    @Column
    private String userNickname;

    @Column
    private String userImg; // 프로필 이미지

    @Column
    private int userFollowerCount; // 팔로워 수

    @Enumerated(EnumType.STRING)
    private Role role; // 역할

    @Enumerated(EnumType.STRING)
    private SocialType socialType; // KAKAO, NAVER, GOOGLE

    private String socialId; // 로그인한 소셜 타입의 식별자 값 (일반 로그인인 경우 null)

    private String refreshToken; // 리프레시 토큰

    // 유저 권한 설정 메소드
    public void authorizeUser() {
        this.role = Role.USER;
    }

    // 비밀번호 암호화 메소드
    public void passwordEncode(PasswordEncoder passwordEncoder) {
        this.userPw = passwordEncoder.encode(this.userPw);
    }

    //== 유저 필드 업데이트 ==//
    public void updateNickname(String updateNickname) {
        this.userNickname = updateNickname;
    }

    public void updatePassword(String updatePassword, PasswordEncoder passwordEncoder) {
        this.userPw = passwordEncoder.encode(updatePassword);
    }

    //RefreshToken 재발급 메소드
    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }

    public User(UserRequestDto requestDto) {
        this.userName = requestDto.getUserName();
        this.userEmail = requestDto.getUserEmail();
        this.userPw = requestDto.getUserPw();
        this.userNickname = requestDto.getUserNickname();
        this.userImg = requestDto.getUserImg();
        this.userFollowerCount = requestDto.getUserFollowerCount();
    }

    public void update(UserRequestDto requestDto, PasswordEncoder passwordEncoder) {
        this.userName = requestDto.getUserName();
        this.userEmail = requestDto.getUserEmail();
        this.userNickname = requestDto.getUserNickname();
        this.userImg = requestDto.getUserImg();
        this.userFollowerCount = requestDto.getUserFollowerCount();

        // 새로운 비밀번호가 null이 아니고, 기존 비밀번호와 다를 때만 인코딩하여 업데이트
        if (requestDto.getUserPw() != null && !requestDto.getUserPw().equals(this.userPw)) {
            this.userPw = passwordEncoder.encode(requestDto.getUserPw());
        }
    }
}
