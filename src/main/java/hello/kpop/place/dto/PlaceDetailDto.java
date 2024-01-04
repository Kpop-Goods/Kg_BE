package hello.kpop.place.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class PlaceDetailDto {
    private int placeCode;
    private Double latitude;
    private Double longitude;
    private String name;
    private String placeAddress;
    private String placeImg;
    private String placeContent;
    private LocalDate startDate;
    private LocalDate endDate;
}
