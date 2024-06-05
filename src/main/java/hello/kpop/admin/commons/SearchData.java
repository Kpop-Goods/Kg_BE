package hello.kpop.admin.commons;

import lombok.Data;

@Data
public class SearchData {

    private int page = 1; //페이지 기본값
    private int limit; // 페이지당 보여질 페이지

    private String sopt;
    private String skey; // 검색 키워드


}
