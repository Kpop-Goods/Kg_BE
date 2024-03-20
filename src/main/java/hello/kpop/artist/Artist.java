package hello.kpop.artist;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hello.kpop.agency.Agency;
import hello.kpop.artist.dto.ArtistDto;
import jakarta.persistence.*;
import lombok.*;
import hello.kpop.place.BaseTimeEntity;
import lombok.*;

import jakarta.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "artist")
public class Artist extends BaseTimeEntity {

    //아티스트 ID (PK)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "artist_id")
    private Long artistId;

    //아티스트 코드
    @Column(name = "artist_cd", nullable = false, length = 100)
    private String artistCd;

    //아티스트 이름
    @Column(name = "artist_name", nullable = false, unique = true, length = 20)
    private String artistName;

    //아티스트 설명
    @Column(name = "comment", length = 500, nullable = false)
    private String comment;

    //아티스트 팔로워 수
    @Column(name = "follow_cnt", columnDefinition = "integer default 0")
    private int followCnt;

    //성별
    @Column(name = "gender", length = 1)
    private String gender;

    //유닛여부
    @Column(name = "unit_yn", length = 1)
    private String unitYN;

    //삭제여부
    @Column(name = "del_yn", length = 1)
    private String delYN;

    //등록 아이디
    @Column(name = "reg_id", length = 100)
    private String regId;

    //Agency 조인
    @JsonIgnore
    @ManyToOne(fetch= FetchType.LAZY) //소속사는 여러 명의 아티스트를 가질 수 있음
    @JoinColumn(name = "agency_fk")
    private Agency agency; // fk(=Agency_pk)

    //Image 조인
//    @OneToMany
//    private List<Image> image;

    public Artist(ArtistDto requestDto, Agency agency) {
        this.artistCd = requestDto.getArtistCd();
        this.artistName = requestDto.getArtistName();
        this.comment = requestDto.getComment();
        this.gender = requestDto.getGender();
        this.unitYN = requestDto.getUnitYN();
        this.regId = requestDto.getRegId();
        this.agency = agency;
    }

    public void update(ArtistDto requestDto) {
        this.artistCd = requestDto.getArtistCd();
        this.artistName = requestDto.getArtistName();
        this.comment = requestDto.getComment();
        this.gender = requestDto.getGender();
        this.unitYN = requestDto.getUnitYN();
    }

    public void updateDelYN(String delYN) {
        this.delYN = delYN;
    }
}
