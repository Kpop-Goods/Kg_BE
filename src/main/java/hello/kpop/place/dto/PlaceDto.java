package hello.kpop.place.dto;

import hello.kpop.artist.Artist;
import hello.kpop.place.Place;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class PlaceDto {

    private int placeCode;
    private Double latitude;
    private Double longitude;
    private String placeName;
    private String placeAddress;
    private String placeImg;
    private String placeContent;
    private int placeFollowerCount;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long artistId;

}
