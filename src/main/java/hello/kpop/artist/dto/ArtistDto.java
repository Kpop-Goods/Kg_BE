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
    private String modId;
    private LocalDateTime modDt;
    private Long agencyId;

}
