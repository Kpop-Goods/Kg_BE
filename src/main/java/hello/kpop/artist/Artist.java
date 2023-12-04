package hello.kpop.artist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data // -> getter랑 setter 역할까지 다 해줌
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

}
