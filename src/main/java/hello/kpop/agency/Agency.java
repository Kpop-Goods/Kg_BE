package hello.kpop.agency;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hello.kpop.image.StringListConverter;
import hello.kpop.socialing.common.entitiy.Base;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import hello.kpop.agency.dto.AgencyDto;
import hello.kpop.artist.Artist;
import hello.kpop.place.BaseTimeEntity;
import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "agency")
@Builder
public class Agency extends Base {

    //소속사 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "agency_id")
    private Long agencyId;

    //소속사 코드
    @Column(name = "agency_cd", length = 100, nullable = false)
    private String agencyCd;

    //소속사 이름
    @Column(name = "agency_name", length = 500, nullable = false)
    private String agencyName;

    //국가 코드
    @Column(name = "country_cd")
    private int countryCd;

    //설립일
    @Column(name = "establish_dt")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate establishDt;

    //설명
    @Column(name = "agency_info", columnDefinition = "TEXT")
    private String agencyInfo;

    //삭제여부
    @Column(name = "del_yn", length = 1)
    private String delYN;

    //등록 아이디
    @Column(name = "reg_id", length = 100)
    private String regId;

    @Convert(converter = StringListConverter.class)
    @Column(name = "image_urls")
    private List<String> imageUrls;

    // 이미지 URL 리스트를 추가하는 메서드
    public void addImageUrl(String imageUrl) {
        this.imageUrls.add(imageUrl);
    }

    // 이미지 URL 리스트를 설정하는 메서드
    public void updateImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    /*
     * 소속사는 여러 아티스트를 가질 수 있으므로
     * OneToMany 사용
     * mappedBy로 연관관계 주인 설정 - 주인 : artist, "artist"가 외래키를 관리
     */
    @JsonIgnore
    @OneToMany(mappedBy = "agency", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Artist> artists = new ArrayList<>();

    public Agency(AgencyDto requestDto) {
        this.agencyCd = requestDto.getAgencyCd();
        this.agencyName = requestDto.getAgencyName();
        this.countryCd = requestDto.getCountryCd();
        this.establishDt = requestDto.getEstablishDt();
        this.agencyInfo = requestDto.getAgencyInfo();
        this.regId = requestDto.getRegId();
    }

    public void update(AgencyDto requestDto) {
        this.agencyCd = requestDto.getAgencyCd();
        this.agencyName = requestDto.getAgencyName();
        this.countryCd = requestDto.getCountryCd();
        this.establishDt = requestDto.getEstablishDt();
        this.agencyInfo = requestDto.getAgencyInfo();
    }

    public void updateDelYN(String delYN) {
        this.delYN = delYN;
    }
}
