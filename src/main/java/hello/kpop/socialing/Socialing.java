package hello.kpop.socialing;

import hello.kpop.artist.Artist;
import hello.kpop.board.dto.Base;
import hello.kpop.socialing.common.SocialingStatus;
import hello.kpop.socialing.dto.SocialingData;
import hello.kpop.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

//엔티티
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Socialing extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long socialingId; // 소셜링 ID (PK)

    @ManyToOne(fetch = FetchType.LAZY) //유저는 여러 개의 생일 카페 및 팝업스토어를 가질 수 있음
    @JoinColumn(name = "user_fk") //artist_id 컬럼과 조인
    private User user; // 유저 ID (FK)

    @Column(nullable = false)
    private  String socialing_name; // 소셜링 명

    @Column
    private String socialing_content; // 소셜링 내용

    @Column
    private String type; //소셜링 타입

    @Column(nullable = false)
    private String social_place; // 장소 (추가)

    @Column
    private LocalDate start_date; // 시작 기간

    @Column
    private LocalDate end_date; // 종료 기간

    @Column(nullable = false)
    private int quota; // 모집 인원

    @Column
    private String chat_url; // 외부 채팅 링크

    @Column(name = "view_cnt")
    private int view; // 조회수

    @Column(name = "like_cnt")
    private int like;  //좋아요

    @Column(name = "follow_cnt")
    private int follow; // 팔로우


    @Enumerated(EnumType.STRING)
    private SocialingStatus del_yn=SocialingStatus.RECRUIT; // 모집 여부

    @ManyToOne(fetch = FetchType.LAZY) //아티스트는 여러 개의 생일 카페 및 팝업스토어를 가질 수 있음
    @JoinColumn(name = "artist_fk") //artist_id 컬럼과 조인
    private Artist artist; // 아티스트 ID (FK)

    //타입 변경
    public void  updateType(SocialingStatus del_yn){
        this.del_yn = del_yn;
    }



    //업데이트용 메서드
    public void modify(SocialingData data,Artist artist){
        this.artist = artist;
        this.socialing_name=data.getSocialing_name();
        this.socialing_content=data.getSocialing_content();
        this.social_place = data.getSocial_place();
        this.type = data.getType();
        this.start_date = data.getStart_date();
        this.end_date = data.getEnd_date();
        this.quota=data.getQuota();
        this.chat_url = data.getChat_url();
        this.del_yn = data.getDel_yn();
    }

}
