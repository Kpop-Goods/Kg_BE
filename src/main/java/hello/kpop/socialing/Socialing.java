package hello.kpop.socialing;

import hello.kpop.artist.Artist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SOCIALING")
public class Socialing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long socialingId; // 소셜링 ID (PK)

    @ManyToOne //아티스트는 여러 개의 생일 카페 및 팝업스토어를 가질 수 있음
    @JoinColumn(name = "artistId") //artist_id 컬럼과 조인
    private Artist artistId; // 아티스트 ID (FK)

//    @ManyToOne //아티스트는 여러 개의 생일 카페 및 팝업스토어를 가질 수 있음
//    @JoinColumn(name = "userId") //artist_id 컬럼과 조인
//    private User userId; // 유저 ID (FK)

    @Temporal(TemporalType.DATE) //년-월-일 이 date타입
    @Column(name = "socialDate", nullable = false)
    private Date socialDate; // 오프라인 만남 날짜

    @Column
    private int socialNop; // 인원수

    @Column
    private String socialPlace; // 위치

    @Column
    private String socialTitle; // 제목

    @Column
    private int socialMoney; // 필요한 금액

    @Column
    private String socialLink; // 외부 채팅 링크

    @Column
    private int socialHeart; // 소셜링 찜 기능

}
