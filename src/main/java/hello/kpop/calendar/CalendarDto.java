package hello.kpop.calendar;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Calendar Common Data Transfer Object(DTO)") // not using "name=" field
public class CalendarDto {

    @Schema(example = "100", description = "unique primary key number for Calendar table")
    private Long id;
    @Schema(example = "100", description = "unique foreign key number by Artist table")
//  private Artist artist; // artist id (fk)
    private Long artistId;

    @Schema(example = "Fan Meeting", description = "Schedule's name")
    private String name;

    @Schema(example = "2021/12/24 23:59:59", description = "Start of schedule including date & time")
    private Date start;
    @Schema(example = "2021/12/25 00:00:01", description = "Start of schedule including date & time")
    private Date end;

    @Schema(example = "http://christmas.org", description = "external link string for schedule")
    private String link;
    @Schema(example = "nothing", description = "meta data, reserved for expansion")
    private String meta;

    CalendarDto(Calendar cal) {
        id = cal.getId();
        artistId = cal.getArtist().getArtistId();
        name = cal.getName();
        start = cal.getStart();
        end = cal.getEnd();
        link = cal.getLink();
        meta = cal.getMeta();
    }
}
