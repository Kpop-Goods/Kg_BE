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
    private String name;
    private String placeAddress;
    private String placeImg;
    private String placeContent;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long artistId;

//    public PlaceDto(Place entity) {
//        this.placeCode = entity.getPlaceCode();
//        this.latitude = entity.getLatitude();
//        this.longitude = entity.getLongitude();
//        this.name = entity.getName();
//        this.placeAddress = entity.getPlaceAddress();
//        this.placeImg = entity.getPlaceImg();
//        this.placeContent = entity.getPlaceContent();
//        this.startDate = entity.getStartDate();
//        this.endDate = entity.getEndDate();
//        this.artistId = entity.getArtist().getArtistId();
//    }
}
