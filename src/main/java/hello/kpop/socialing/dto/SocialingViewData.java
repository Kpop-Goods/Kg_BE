package hello.kpop.socialing.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SocialingViewData {


    private String nickname; // 등록한 유저 닉네임
    private String artistName; //아티스트 이름
    private String agencyName; // 소속사
    private String socialing_name; //제목
    private String socialing_content;// 내용
    private String type; //소셜 타입
    private int quota;//모집 인원
    private String chat_url; // 외부 채팅 링크
    private LocalDate start_date; // 시작 기간
    private LocalDate end_date; // 종료 기간
    private int view; // 조회수
    private int follow; // 팔로우
    private int like;  //좋아요

    private List<SocialingListData> ViewDesc; //조회수 목록
    private List<SocialingListData> LikeDesc; // 좋아요 목록

}
