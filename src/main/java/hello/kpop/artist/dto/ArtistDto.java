package hello.kpop.artist.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class ArtistDto {

    private String artistCd;
    private String artistName;
    private String comment;
    private String gender;
    private String unitYN;
    private String regId;
    private LocalDateTime regDt;
    private LocalDateTime modDt;
    private Long agencyId;

    public void updateRegId(String regId) {
        this.regId = regId;
    }

}
