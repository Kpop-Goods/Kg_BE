package hello.kpop.artist.dto;

import hello.kpop.artist.Artist;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ArtistResponseDto {

    private Long artistId;
    private String artistCd;
    private String artistName;
    private String comment;
    private int followCnt;
    private String gender;
    private String unitYN;
    private String delYN;
    private String regId;
    private LocalDateTime regDt;
    private LocalDateTime modDt;
    private Long agencyId;

    public ArtistResponseDto(Artist entity) {
        this.artistId = entity.getArtistId();
        this.artistCd = entity.getArtistCd();
        this.artistName = entity.getArtistName();
        this.comment = entity.getComment();
        this.followCnt = entity.getFollowCnt();
        this.gender = entity.getGender();
        this.unitYN = entity.getUnitYN();
        this.delYN = entity.getDelYN();
        this.regId = entity.getRegId();
        this.regDt = entity.getRegDt();
        this.modDt = entity.getMod_dt();
        this.agencyId = entity.getAgency().getAgencyId();
    }
}
