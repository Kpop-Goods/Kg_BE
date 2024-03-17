package hello.kpop.place.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@Getter
@Setter
public class PlaceDto {

    private Integer eventCategoryCd;
    private String eventName;
    private String placeName;
    private String address; //우편번호
    private String streetAddress; //지번주소
    private String detailAddress; //상세주소
    private String content;
    private String notes;
    private String eventUrl;
    private Double latitude;
    private Double longitude;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String regId;
    private String modId;
    private Long artistId;

}
