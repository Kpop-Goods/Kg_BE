package hello.kpop.board.dto;

import lombok.Data;

import java.util.List;

@Data
public class BoardSearchData {

    private int page = 1;
    private int limit;

    private String sopt; //옵션검색
    private String skey; //옵션검색 키워드

    private String subject;
    private String content;

    private List<Boolean> del_yn;
    private List<Boolean> notice_yn;


   // private LocalDateTime regDt;

}
