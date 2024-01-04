package hello.kpop.place;

import com.fasterxml.jackson.annotation.JsonFormat;
import hello.kpop.artist.Artist;
import hello.kpop.place.dto.PlaceDetailDto;
import hello.kpop.place.dto.PlaceDto;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data // -> getter랑 setter 역할까지 다 해줌
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "PLACE")
@Builder
public class Place {

    //장소ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "placeId")
    private Long placeId;

    //장소코드 - 예)생일카페 - 1 / 팝업스토어 - 2
    //해당 컬럼 프런트와 협의 후 수정할 예정 - placeCode 존재유무에 대한 협의
    @Column(name = "placeCode", nullable = false)
    private int placeCode;

    //위치
    //위도
    @Column(name = "latitude", nullable = false)
    private Double latitude;
    //경도
    @Column(name = "longitude", nullable = false)
    private Double longitude;

    //이벤트 명
    @Column(name = "placeName", nullable = false)
    private String placeName;

    //주소
    @Column(name = "placeAddress", nullable = false)
    private String placeAddress;

    //장소 이미지
    @Column(name = "placeImg", nullable = false)
    private String placeImg; //이미지 경로

    //이벤트 설명
    @Column(name = "placeContent", columnDefinition = "TEXT", nullable = false)
    private String placeContent;

    //장소 팔로우
    @Column(name = "placeFollowerCount")
    @ColumnDefault("0") //default
    private int placeFollowerCount;

    //시작 날짜
    @Column(name = "startDate", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate startDate;

    //종료 날짜
    @Column(name = "endDate", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate endDate;

    //Artist 조인
    @ManyToOne(fetch= FetchType.LAZY) //아티스트는 여러 개의 생일 카페 및 팝업스토어를 가질 수 있음
    @JoinColumn(name = "artistId")
    private Artist artist; //fk(=Artist_pk)

    //artist 매핑
    public Place(Artist artist, PlaceDto requestDto) {
        this.placeCode = requestDto.getPlaceCode();
        this.latitude = requestDto.getLatitude();
        this.longitude = requestDto.getLongitude();
        this.placeName = requestDto.getPlaceName();
        this.placeAddress = requestDto.getPlaceAddress();
        this.placeImg = requestDto.getPlaceImg();
        this.placeContent = requestDto.getPlaceContent();
        this.placeFollowerCount = requestDto.getPlaceFollowerCount();
        this.startDate = requestDto.getStartDate();
        this.endDate = requestDto.getEndDate();
        this.artist = artist;
    }

    public void update(PlaceDetailDto detailRequestDto) {
        this.placeCode = detailRequestDto.getPlaceCode();
        this.latitude = detailRequestDto.getLatitude();
        this.longitude = detailRequestDto.getLongitude();
        this.placeName = detailRequestDto.getPlaceName();
        this.placeAddress = detailRequestDto.getPlaceAddress();
        this.placeImg = detailRequestDto.getPlaceImg();
        this.placeContent = detailRequestDto.getPlaceContent();
        this.placeFollowerCount = detailRequestDto.getPlaceFollowerCount();
        this.startDate = detailRequestDto.getStartDate();
        this.endDate = detailRequestDto.getEndDate();
    }
}
