package hello.kpop.socialing.dto;


import hello.kpop.socialing.common.SocialingStatus;
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
public class SocialViewData {


    private Long Id;
    private String nickname; // 등록한 유저 닉네임
    private String artistName; //아티스트 이름
    private String agency; // 소속사
    private String socialing_name; //제목
    private String socialing_content;// 내용
    private int quota;//모집 인원
    private SocialingStatus del_yn; //소셜 타입
    private String chat_url; // 외부 채팅 링크
    private LocalDate start_date; // 시작 기간
    private LocalDate end_date; // 종료 기간
    private int view_cnt; // 조회수
    private int follow_cnt; // 팔로우
    private int like_cnt;  //좋아요

    private List<SocialingListData> ViewDesc; //조회수 목록
    private List<SocialingListData> LikeDesc; // 좋아요 목록


}
