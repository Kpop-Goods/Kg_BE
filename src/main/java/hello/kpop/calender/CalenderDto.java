package hello.kpop.calender;

import hello.kpop.artist.Artist;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Getter
public class CalenderDto {

    private Long id; // id (pk)
    private Artist artist; // artist id (fk)

    private String name; // name

    private Date start; // start date, time
    private Date end; // end date, time

    private String link; // external link
    private String meta; // meta data
}
