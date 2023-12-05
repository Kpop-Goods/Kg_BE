package hello.kpop.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserSignUpDto {

    private String userName; // 이름
    private String userEmail; // 이메일
    private String userPw; // 비밀번호
    private String userNickname; // 닉네임

}
