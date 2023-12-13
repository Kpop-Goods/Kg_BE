package hello.kpop.socialing.dto;

import hello.kpop.artist.Artist;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Getter
public class SocialingDto {

    private Artist artistId; // 아티스트 ID (FK)

//    private User userId; // 유저 ID (FK)

    private Date socialDate; // 오프라인 만남 날짜

    private int socialNop; // 인원수

    private String socialPlace; // 위치

    private String socialTitle; // 제목

    private int socialMoney; // 필요한 금액

    private String socialLink; // 외부 채팅 링크

    private int socialHeart; // 소셜링 찜 기능

}
