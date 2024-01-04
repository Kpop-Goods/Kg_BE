package hello.kpop.place.dto;

import hello.kpop.artist.Artist;
import hello.kpop.place.Place;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PlaceResponseDto {

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

    public PlaceResponseDto(Place entity) {
        this.placeCode = entity.getPlaceCode();
        this.latitude = entity.getLatitude();
        this.longitude = entity.getLongitude();
        this.placeName = entity.getPlaceName();
        this.placeAddress = entity.getPlaceAddress();
        this.placeImg = entity.getPlaceImg();
        this.placeContent = entity.getPlaceContent();
        this.placeFollowerCount = entity.getPlaceFollowerCount();
        this.startDate = entity.getStartDate();
        this.endDate = entity.getEndDate();
        this.artistId = entity.getArtist().getArtistId();
    }
}
