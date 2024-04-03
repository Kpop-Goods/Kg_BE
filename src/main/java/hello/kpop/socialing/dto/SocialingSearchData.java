package hello.kpop.socialing.dto;

import lombok.Data;

//페이징과 검색 데이터
@Data
public class SocialingSearchData {

    private int page = 1; //페이지 기본값
    private int limit; // 페이지당 보여질 페이지

    private String sopt;
    private String skey; // 검색 키워드


    private String socialing_name; // 제목
    private String artist; // 아티스트
    private String type; // 타입
    private String place; // 지역



}
