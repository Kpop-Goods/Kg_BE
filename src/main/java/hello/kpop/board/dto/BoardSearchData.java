package hello.kpop.board.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardSearchData {

    private int page = 1;
    private int limit;

    private String subject;

    private String content;
    private LocalDateTime regDt;

}
