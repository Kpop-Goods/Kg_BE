package hello.kpop.artist;

import hello.kpop.agency.Agency;
import hello.kpop.artist.dto.ArtistDto;
import hello.kpop.place.Place;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data // -> getter랑 setter 역할까지 다 해줌
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "ARTIST")
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long artistId; // 아티스트 ID (PK)

    @Column
    private String artistImg; // 대표 이미지

    @Column(name = "artistContent", columnDefinition = "TEXT", nullable = false)
    private String artistContent; // 아티스트 설명

    @Column(nullable = false, unique = true)
    private String artistName; // 아티스트 이름

    @Column
    private int artistCount; // 팔로워수

    //Agency 조인
    @ManyToOne(fetch= FetchType.LAZY) //소속사는 여러 명의 아티스트를 가질 수 있음
    @JoinColumn(name = "agencyId")
    private Agency agency; // fk(=Agency_pk)

    public Artist(ArtistDto requestDto) {
        this.artistImg = requestDto.getArtistImg();
        this.artistContent = requestDto.getArtistContent();
        this.artistName = requestDto.getArtistName();
        this.artistCount = requestDto.getArtistCount();
    }

    public void update(ArtistDto requestDto) {
        this.artistImg = requestDto.getArtistImg();
        this.artistContent = requestDto.getArtistContent();
        this.artistName = requestDto.getArtistName();
        this.artistCount = requestDto.getArtistCount();
    }
}
