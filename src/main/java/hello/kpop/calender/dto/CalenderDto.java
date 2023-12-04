package hello.kpop.calender.dto;

import hello.kpop.artist.Artist;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Getter
public class CalenderDto {

    private Artist artistId; // 아티스트 ID (FK)

    private Date calenderDate; // 스케줄 만남 날짜

    private String calenderName; // 스케줄 이름

    private String calenderLink; // 스케줄 위치 or 온라인 링크

}
