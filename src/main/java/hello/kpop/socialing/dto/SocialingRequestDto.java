package hello.kpop.socialing.dto;

import hello.kpop.socialing.SocialingStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

//요청 데이터
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SocialingRequestDto {

    private Long socialingId; //소셜링 ID

    private Long userId; // 등록한 유저 아이디

    private Long artistId; //아티스트 코드

    @NotBlank( message = "제목을 입력해주세요")
    private String socialing_name; //제목

    private String socialing_content;// 내용

    @NotBlank(message = "타입을 적어주세요")
    private String type; //소셜링 타입

    @NotBlank(message = "장소를 입력해주세요")
    private String social_place;// 장소


    private Date start_date; // 시작일자

    private Date end_date;// 종료일자

    @NotNull(message = "인워수를 적어주세요")
    private int quota;//모집 인원

    private int found_raised; //모금액

    @NotBlank
    @Pattern(regexp = "^(https?|ftp)://[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)+([/?].*)?$", message = "올바르지 않은 URL주소입니다.")
    private String chat_url;// 외부 채팅 링크

    private int count; //조회수


    private SocialingStatus del_yn; // 사용여부

    private LocalDateTime reg_date; // 등록 일자

    private LocalDateTime mod_date;// 수정 일자


    private String imgUrl; //사진


}
