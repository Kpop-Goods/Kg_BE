package hello.kpop.calender;

import hello.kpop.artist.Artist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data // -> getter랑 setter 역할까지 다 해줌
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CALENDER")
public class Calender {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long calenderId; // 캘린더 ID (PK)

    @ManyToOne //아티스트는 여러 개의 생일 카페 및 팝업스토어를 가질 수 있음
    @JoinColumn(name = "artistId") //artist_id 컬럼과 조인
    private Artist artistId; // 아티스트 ID (FK)

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "calenderDate", nullable = false)
    private Date calenderDate; // 스케줄 만남 날짜

    @Column
    private String calenderName; // 스케줄 이름

    @Column
    private String calenderLink; // 스케줄 위치 or 온라인 링크

}
