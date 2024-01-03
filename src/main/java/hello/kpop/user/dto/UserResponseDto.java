package hello.kpop.user.dto;


import hello.kpop.user.Role;
import hello.kpop.user.SocialType;
import hello.kpop.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserResponseDto {

    private Long userId;
    private String userName;
    private String userEmail;
    private String userNickname;
    private String userImg;
    private int userFollowerCount;
    private Role role;
    private SocialType socialType;
    private String socialId;

    public UserResponseDto(User entity) {
        this.userId = entity.getUserId();
        this.userName = entity.getUserName();
        this.userEmail = entity.getUserEmail();
        this.userNickname = entity.getUserNickname();
        this.userImg = entity.getUserImg();
        this.userFollowerCount = entity.getUserFollowerCount();
        this.role = entity.getRole();
        this.socialType = entity.getSocialType();
        this.socialId = entity.getSocialId();
    }
}
