package hello.kpop.socialing.dto;

import hello.kpop.artist.Artist;
import hello.kpop.socialing.Socialing;
import hello.kpop.socialing.SocialingStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

//응답 데이터
@Data
@NoArgsConstructor
public class SocialingResponseDto {

    private Long socialingId; //소셜링 ID

    private Long userId; // 등록한 유저 아이디

    private Artist artist;

    private String socialing_name; //제목

    private String socialing_content;// 내용

    private Date start_date; // 시작일자

    private Date end_date;// 종료일자

    private int quota;//모집 인원

    private int found_raised; //모금액

    private String chat_url;// 외부 채팅 링크

    private int count; //조회수

    private SocialingStatus del_yn; // 사용여부

    private LocalDateTime reg_date; // 등록 일자

    private LocalDateTime mod_date;// 수정 일자

    private String social_place;// 장소



    public SocialingResponseDto(Socialing socialing) {
        this.socialingId = socialing.getSocialingId();
        this.artist = socialing.getArtistId();
        this.socialing_name=socialing.getSocialing_name();
        this.socialing_content=socialing.getSocialing_content();
        this.start_date = socialing.getStart_date();
        this.end_date = socialing.getEnd_date();
        this.quota=socialing.getQuota();
        this.found_raised=socialing.getFunds_raised();
        this.chat_url = socialing.getChat_url();
        this.count =socialing.getCount();
        this.del_yn = socialing.getDel_yn();
        this.reg_date = socialing.getReg_dt();
        this.mod_date = socialing.getMod_dt();
        this.social_place=socialing.getSocial_place();
    }
}

