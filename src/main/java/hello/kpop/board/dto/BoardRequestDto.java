package hello.kpop.board.dto;

import lombok.*;
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardRequestDto{


    private String password;
    private String type;
    private String subject;
    private String content;
    private String imgUrl;

}

