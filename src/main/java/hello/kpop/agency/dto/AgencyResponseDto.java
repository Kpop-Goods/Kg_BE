package hello.kpop.agency.dto;

import hello.kpop.agency.Agency;
import hello.kpop.artist.Artist;
import hello.kpop.artist.dto.ArtistResponseDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class AgencyResponseDto {

    private Long agencyId;
    private String agencyCd;
    private String agencyName;
    private int countryCd;
    private LocalDate establishDt;
    private String agencyInfo;
    private String delYN;
    private String regId;
    private LocalDateTime regDt;
    private LocalDateTime modDt;

    private List<Artist> artists = new ArrayList<>();

    public AgencyResponseDto(Agency entity) {
        this.agencyId = entity.getAgencyId();
        this.agencyCd = entity.getAgencyCd();
        this.agencyName = entity.getAgencyName();
        this.countryCd = entity.getCountryCd();
        this.establishDt = entity.getEstablishDt();
        this.agencyInfo = entity.getAgencyInfo();
        this.delYN = entity.getDelYN();
        this.regId = entity.getRegId();
        this.regDt = entity.getRegDt();
        this.modDt = entity.getModDt();
        this.artists = entity.getArtists().stream().toList();
    }
}
