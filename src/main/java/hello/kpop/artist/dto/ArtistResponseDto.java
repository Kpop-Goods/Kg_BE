package hello.kpop.artist.dto;

import hello.kpop.artist.Artist;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArtistResponseDto {

    private Long artistId;
    private String artistImg;
    private String artistContent;
    private String artistName;
    private int artistCount;

    public ArtistResponseDto(Artist entity) {
        this.artistId = entity.getArtistId();
        this.artistImg = entity.getArtistImg();
        this.artistContent = entity.getArtistContent();
        this.artistName = entity.getArtistName();
        this.artistCount = entity.getArtistCount();
    }
}
