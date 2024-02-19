package hello.kpop.socialing.dto;

import lombok.Data;

//페이징과 검색 데이터
@Data
public class SocialingSearchDto {

    private int page = 1; //페이지 기본값
    private int limit; // 페이지당 보여질 페이지

    private String sopt; // 검색 옵션
    private String skey; // 검색 키워드

    private String type;
    private String socialPlace;



}
