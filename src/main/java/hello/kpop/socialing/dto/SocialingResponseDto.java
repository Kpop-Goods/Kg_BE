package hello.kpop.socialing.dto;

import hello.kpop.socialing.Socialing;
import hello.kpop.socialing.SocialingStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

//응답 데이터
@Data
@NoArgsConstructor
public class SocialingResponseDto {

    private Long socialingId; //소셜링 ID

    private Long userId; // 등록한 유저 아이디

    private String artist_name; // 아티스트 이름

    private String socialing_name; //제목

    private String socialing_content;// 내용

    private String type; //타입

    private String social_place;// 장소


    private LocalDateTime start_date; // 시작일자

    private LocalDateTime end_date;// 종료일자

    private int quota;//모집 인원

    private int found_raised; //모금액

    private String chat_url;// 외부 채팅 링크

    private int view_ctn; //조회수

    private int follow_cnt; //팔로우

    private int like_cnt; // 좋아요

    private SocialingStatus del_yn; // 사용여부

    private LocalDateTime reg_date; // 등록 일자

    private LocalDateTime mod_date;// 수정 일자




    public SocialingResponseDto(Socialing socialing) {
        this.socialingId = socialing.getSocialingId();
        this.artist_name = socialing.getArtistId().getArtistName();
        this.socialing_name=socialing.getSocialing_name();
        this.socialing_content=socialing.getSocialing_content();
        this.start_date = socialing.getStart_date();
        this.end_date = socialing.getEnd_date();
        this.quota=socialing.getQuota();
        this.found_raised=socialing.getFunds_raised();
        this.chat_url = socialing.getChat_url();
        this.view_ctn =socialing.getView_ctn();
        this.del_yn = socialing.getDel_yn();
        this.reg_date = socialing.getReg_dt();
        this.mod_date = socialing.getMod_dt();
        this.type = socialing.getType();
        this.social_place=socialing.getSocial_place();
    }
}

