package hello.kpop.socialing.dto;


import hello.kpop.socialing.Socialing;
import hello.kpop.socialing.SocialingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SocialViewData {


    private String NicName; // 등록한 유저 닉네임
    private String artist_name; //아티스트 이름
    private String socialing_name; //제목
    private String socialing_content;// 내용
    private int quota;//모집 인원
    private SocialingStatus del_yn; //소셜 타입
    private LocalDateTime start_date; // 시작 기간
    private LocalDateTime end_date; // 종료 기간
    private int view_count; //조회수

    private List<Socialing> ViewCountAsc; //조회수 목록
    private List<Socialing> LikeCountAsc; // 좋아요 목록

}
