package hello.kpop.socialing.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import hello.kpop.socialing.SocialingStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

//소셜링 등록 요청 데이터
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SocialingData {



    private String nickname; // 등록한 유저 닉네임

    @NotBlank
    private String artistName; //아티스트 이름

    @NotBlank
    private String socialing_name; //제목

    private String socialing_content;// 내용

    @NotBlank
    private String type; //소셜링 타입

    @NotBlank
    private String social_place;// 장소

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate start_date; // 시작일자

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate end_date;// 종료일자

    @NotNull
    @Min(value = 1)
    @Max(value = 10)
    private int quota;//모집 인원

    //    @Pattern(regexp = "^(https?|ftp)://[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)+([/?].*)?$")
    private String chat_url;// 외부 채팅 링크

    @NotNull
    private SocialingStatus del_yn; // 모집 여부



}


