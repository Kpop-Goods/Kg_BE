package hello.kpop.socialing;

import hello.kpop.artist.Artist;
import hello.kpop.board.dto.Base;
import hello.kpop.socialing.dto.SocialingRequestDto;
import hello.kpop.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

//엔티티
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "socialing")
public class Socialing extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long socialingId; // 소셜링 ID (PK)

    @ManyToOne(fetch = FetchType.LAZY) //아티스트는 여러 개의 생일 카페 및 팝업스토어를 가질 수 있음
    @JoinColumn(name = "artist_fk") //artist_id 컬럼과 조인
    private Artist artistId; // 아티스트 ID (FK)

    @ManyToOne(fetch = FetchType.LAZY) //유저는 여러 개의 생일 카페 및 팝업스토어를 가질 수 있음
    @JoinColumn(name = "user_fk") //artist_id 컬럼과 조인
    private User userId; // 유저 ID (FK)

    @Column(nullable = false)
    private  String socialing_name; // 소셜링 명

    @Column
    private String socialing_content; // 소셜링 내용

    @Column
    private String type; //소셜링 타입

    @Column(nullable = false)
    private String social_place; // 장소 (추가)

    @Column
    private LocalDateTime start_date; // 시작 기간

    @Column
    private LocalDateTime end_date; // 종료 기간

    @Column(nullable = false)
    private int quota; // 모집 인원

    @Column
    private int funds_raised; // 모금액

    @Column
    private String chat_url; // 외부 채팅 링크

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int view_ctn; // 조회수

    @Column
    private int follow_cnt; // 팔로우

    @Column
    private int like_cnt;  //좋아요

    @Enumerated(EnumType.STRING)
    private SocialingStatus del_yn=SocialingStatus.RECRUIT; // 모집 여부

    public void  updateType(SocialingStatus del_yn){
        this.del_yn = del_yn;
    }




    //요청 데이터 엔티티 변환
    public Socialing(SocialingRequestDto socialingRequestDto){
        this.socialingId = socialingRequestDto.getSocialingId();

        this.artistId = socialingRequestDto.getArtist_name();

        this.socialing_name=socialingRequestDto.getSocialing_name();
        this.socialing_content=socialingRequestDto.getSocialing_content();
        this.social_place = socialingRequestDto.getSocial_place();
        this.type = socialingRequestDto.getType();
        this.start_date = socialingRequestDto.getStart_date();
        this.end_date = socialingRequestDto.getEnd_date();
        this.quota=socialingRequestDto.getQuota();
        this.funds_raised=socialingRequestDto.getFound_raised();
        this.chat_url = socialingRequestDto.getChat_url();
        this.view_ctn =socialingRequestDto.getView_ctn();
        this.del_yn = socialingRequestDto.getDel_yn();
    }

    //업데이트용 메서드
    public void modify(SocialingRequestDto socialingRequestDto){

        this.artistId = socialingRequestDto.getArtist_name();

        this.socialing_name=socialingRequestDto.getSocialing_name();
        this.socialing_content=socialingRequestDto.getSocialing_content();
        this.social_place = socialingRequestDto.getSocial_place();
        this.type = socialingRequestDto.getType();
        this.start_date = socialingRequestDto.getStart_date();
        this.end_date = socialingRequestDto.getEnd_date();
        this.quota=socialingRequestDto.getQuota();
        this.funds_raised=socialingRequestDto.getFound_raised();
        this.chat_url = socialingRequestDto.getChat_url();
        this.del_yn = socialingRequestDto.getDel_yn();
    }

}
