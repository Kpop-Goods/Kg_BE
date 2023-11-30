package hello.kpop.place;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "PLACES")
@Builder
public class Place {

    //장소ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "placeId")
    private Long placeId;

    //장소코드 - 예)생일카페 - 1 / 팝업스토어 - 2
    @Column(name = "placeCode", nullable = false)
    private Integer placeCode;

    //위치
    //위도
    @Column(name = "latitude", nullable = false)
    private Double latitude;
    //경도
    @Column(name = "hardness", nullable = false)
    private Double hardness;

    //장소명
    @Column(name = "placeName", nullable = false)
    private String placeName;

    //장소 이미지 (몇 개로 구성할 것인지?)
    @Column(name = "placeImg", nullable = false)
    private String placeImg; //이미지 경로

    //장소 설명
    @Column(name = "placeContent", columnDefinition = "TEXT", nullable = false)
    private String placeContent;

    //시작 날짜
    @Temporal(TemporalType.DATE) //년-월-일 이 date타입
    @Column(name = "startDate", nullable = false)
    private Date startDate;

    //종료 날짜
    @Temporal(TemporalType.DATE) //년-월-일 이 date타입
    @Column(name = "endDate", nullable = false)
    private Date endDate;

    //아티스트 번호
//    @ManyToOne //아티스트는 여러 개의 생일 카페 및 팝업스토어를 가질 수 있음
//    @JoinColumn(name = "artistId") //artist_id 컬럼과 조인
//    private Artist artistId;
}
