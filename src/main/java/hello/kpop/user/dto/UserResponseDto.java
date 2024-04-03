package hello.kpop.user.dto;


import hello.kpop.user.Role;
import hello.kpop.user.SocialType;
import hello.kpop.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponseDto {

    private Long id;
    private String userName;
    private String userEmail;
    private String nickname;
    private Role userType;
    private SocialType socialType;
    private String socialId;
    private Integer followCnt;

    public UserResponseDto(User entity) {
        this.id = entity.getId();
        this.userName = entity.getUserName();
        this.userEmail = entity.getUserEmail();
        this.nickname = entity.getNickname();
        this.userType = entity.getUserType();
        this.socialType = entity.getSocialType();
        this.socialId = entity.getSocialId();
        this.followCnt = entity.getFollowCnt();
    }
}
