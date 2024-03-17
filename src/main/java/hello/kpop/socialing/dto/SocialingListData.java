package hello.kpop.socialing.dto;


import hello.kpop.socialing.Socialing;
import hello.kpop.socialing.common.SocialingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SocialingListData {


 private Long socialingId;
 private String nickname;
 private String artistName;
 private String agency;
 private String socialingName;
 private SocialingStatus del_yn;
 private int quota;
 private int view; // 조회수
 private int like;  //좋아요



 public SocialingListData(Socialing socialing){
  this.socialingId=socialing.getSocialingId();
  this.nickname = socialing.getUser().getNickname();
  this.artistName = socialing.getArtist().getArtistName();
  this.agency = socialing.getArtist().getAgency().getAgencyName();
  this.socialingName = socialing.getSocialing_name();
  this.del_yn = socialing.getDel_yn();
  this.view=socialing.getView();
  this.like=socialing.getLike();
  this.quota = socialing.getQuota();

 }


}
