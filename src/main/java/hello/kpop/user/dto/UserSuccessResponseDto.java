package hello.kpop.user.dto;

import lombok.Getter;

@Getter
public class UserSuccessResponseDto {
    private boolean success;

    public UserSuccessResponseDto(boolean success) {
        this.success = success;
    }
}
