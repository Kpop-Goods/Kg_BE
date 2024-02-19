package hello.kpop.socialing.dto;


import hello.kpop.socialing.Socialing;
import hello.kpop.socialing.SocialingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class SocialingListDto {


   private String userId; // 등록한 유저 닉네임
    private String artist_name; //아티스트 이름
    private String socialing_name; //제목
    private String socialing_content;// 내용
    private int quota;//모집 인원
    private SocialingStatus del_yn; //소셜 타입



    public  SocialingListDto (Socialing socialing) {
        this.userId = socialing.getUserId().getUserName();
        this.artist_name = socialing.getArtistId().getArtistName();
        this.socialing_name=socialing.getSocialing_name();
        this.socialing_content=socialing.getSocialing_content();
        this.quota=socialing.getQuota();
        this.del_yn=socialing.getDel_yn();

    }

}
