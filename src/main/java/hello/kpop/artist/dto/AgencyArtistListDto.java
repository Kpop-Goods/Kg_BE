package hello.kpop.artist.dto;

import hello.kpop.artist.Artist;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AgencyArtistListDto {

    private Long artistId;
    private String artistName;
    private Long agencyId;
    private String agencyName;

    public AgencyArtistListDto(Artist entity) {
        this.artistId = entity.getArtistId();
        this.artistName = entity.getArtistName();
        this.agencyId = entity.getAgency().getAgencyId();
        this.agencyName = entity.getAgency().getAgencyName();
    }
}
