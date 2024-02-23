package hello.kpop.socialing.dto;

import hello.kpop.artist.Artist;
import hello.kpop.socialing.SocialingStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

//요청 데이터
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SocialingRequestDto {

    private Long socialingId; //소셜링 ID

    private Long userId; // 등록한 유저 아이디

    private Artist artist_name; //아티스트 이름

    @NotBlank
    private String socialing_name; //제목

    private String socialing_content;// 내용

    @NotBlank
    private String type; //소셜링 타입

    @NotBlank
    private String social_place;// 장소

   // @NotBlank
    private LocalDateTime start_date; // 시작일자

   // @NotBlank
    private LocalDateTime end_date;// 종료일자

    @NotNull
    private int quota;//모집 인원

    private int found_raised; //모금액

    @NotBlank
//    @Pattern(regexp = "^(https?|ftp)://[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)+([/?].*)?$")
    private String chat_url;// 외부 채팅 링크

    private int view_ctn; //조회수

    private SocialingStatus del_yn; // 사용여부

    private LocalDateTime reg_date; // 등록 일자

    private LocalDateTime mod_date;// 수정 일자

}
