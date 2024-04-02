package hello.kpop.board.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardViewData {

    private String nickName ; // 등록한 닉네임

    private String subject; // 제목

    private String content; // 내용


    private LocalDateTime regDt; // 생성일자


}
