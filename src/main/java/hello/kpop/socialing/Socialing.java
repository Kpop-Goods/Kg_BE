package hello.kpop.socialing;

import hello.kpop.artist.Artist;
import hello.kpop.board.dto.Base;
import hello.kpop.socialing.dto.SocialingRequestDto;
import hello.kpop.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.Date;

import static hello.kpop.socialing.SocialingStatus.RECRUIT;

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
    private String social_place; // 위치 (추가)

    @Column
    private Date start_date; // 시작 기간

    @Column
    private Date end_date; // 종료 기간

    @Column(nullable = false)
    private int quota; // 모집 인원

    @Column
    private int funds_raised; // 모금액

    @Column
    private String chat_url; // 외부 채팅 링크

    @Column
    private int count; // 조회수

    @Enumerated(EnumType.STRING)
    private SocialingStatus del_yn=RECRUIT; // 사용 여부

    @Column
    private String imgUrl; // 사진 (추가)



    //요청 데이터 엔티티 변환
    public Socialing(SocialingRequestDto socialingRequestDto){
        this.socialingId = socialingRequestDto.getSocialingId();
        this.socialing_name=socialingRequestDto.getSocialing_name();
        this.socialing_content=socialingRequestDto.getSocialing_content();
        this.start_date = socialingRequestDto.getStart_date();
        this.end_date = socialingRequestDto.getEnd_date();
        this.quota=socialingRequestDto.getQuota();
        this.funds_raised=socialingRequestDto.getFound_raised();
        this.chat_url = socialingRequestDto.getChat_url();
        this.count =socialingRequestDto.getCount();
        this.del_yn = socialingRequestDto.getDel_yn();
        this.social_place = socialingRequestDto.getSocial_place();
        this.imgUrl = socialingRequestDto.getImgUrl();
    }

    //업데이트용 메서드
    public void modify(SocialingRequestDto socialingRequestDto){
        this.socialing_name=socialingRequestDto.getSocialing_name();
        this.socialing_content=socialingRequestDto.getSocialing_content();
        this.start_date = socialingRequestDto.getStart_date();
        this.end_date = socialingRequestDto.getEnd_date();
        this.quota=socialingRequestDto.getQuota();
        this.funds_raised=socialingRequestDto.getFound_raised();
        this.chat_url = socialingRequestDto.getChat_url();
        this.del_yn = socialingRequestDto.getDel_yn();
        this.social_place = socialingRequestDto.getSocial_place();
        this.imgUrl = socialingRequestDto.getImgUrl();
    }


}
