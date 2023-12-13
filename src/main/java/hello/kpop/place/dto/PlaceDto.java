package hello.kpop.place.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Getter
public class PlaceDto {

    private Long placeCode;
    private Double latitude;
    private Double longitude;
    private String name;
    private String placeAddress;
    private String placeImg;
    private String placeContent;
    private Date startDate;
    private Date endDate;
//    private Artist artistId;

}
