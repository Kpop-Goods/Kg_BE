package hello.kpop.artist.dto;

import hello.kpop.artist.Artist;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArtistAgencyDto {

    private Long artistId;
    private String artistName;
    private Long agencyId;
    private String agencyName;

    public ArtistAgencyDto(Artist entity) {
        this.artistId = entity.getArtistId();
        this.artistName = entity.getArtistName();
        this.agencyId = entity.getAgency().getAgencyId();
        this.agencyName = entity.getAgency().getAgencyName();
    }
}
