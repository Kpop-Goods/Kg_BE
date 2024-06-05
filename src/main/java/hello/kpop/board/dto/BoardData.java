package hello.kpop.board.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardData {

    @NotBlank
    private String subject; //제목

    @NotBlank
    private String content; //내용

    private boolean notice_yn; // 노출 여부

    private boolean del_yn; // 삭제 여부

    private LocalDateTime regDt; // 생성 일자

    private String createdBy; // 작성자

    private String mode;

}
