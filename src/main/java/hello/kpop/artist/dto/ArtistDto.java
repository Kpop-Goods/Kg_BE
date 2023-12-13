package hello.kpop.artist.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ArtistDto {

    private String artistImg;
    private String artistContent;
    private String artistName;
    private int artistCount;

}
