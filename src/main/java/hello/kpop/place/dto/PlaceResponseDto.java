package hello.kpop.place.dto;

import hello.kpop.place.Place;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
public class PlaceResponseDto {

    private Long eventId;
    private Integer eventCategoryCd;
    private String eventName;
    private String placeName;
    private String address;
    private String streetAddress;
    private String detailAddress;
    private String content;
    private String notes;
    private String eventUrl;
    private Double latitude;
    private Double longitude;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private int count;
    private int followCnt;
    private String delYN;
    private String regId;
    private LocalDateTime regDt;
    private LocalDateTime modDt;
    private Long artistId;
    private Long agencyId;

    public PlaceResponseDto(Place entity) {
        this.eventId = entity.getEventId();
        this.eventCategoryCd = entity.getEventCategoryCd();
        this.eventName = entity.getEventName();
        this.placeName = entity.getPlaceName();
        this.address = entity.getAddress();
        this.streetAddress = entity.getStreetAddress();
        this.detailAddress = entity.getDetailAddress();
        this.content = entity.getContent();
        this.notes = entity.getNotes();
        this.eventUrl = entity.getEventUrl();
        this.latitude = entity.getLatitude();
        this.longitude = entity.getLongitude();
        this.startDate = entity.getStartDate();
        this.endDate = entity.getEndDate();
        this.startTime = entity.getStartTime();
        this.endTime = entity.getEndTime();
        this.count = entity.getCount();
        this.followCnt = entity.getFollowCnt();
        this.delYN = entity.getDelYN();
        this.regId = entity.getRegId();
        this.regDt = entity.getRegDt();
        this.modDt = entity.getModDt();
        this.artistId = entity.getArtist().getArtistId();
        this.agencyId = entity.getArtist().getAgency().getAgencyId();
    }

}
