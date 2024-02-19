package hello.kpop.board.dto;


import lombok.*;

import java.time.LocalDateTime;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardResponseDto {


    private Long noticeId;
    private String subject;
    private String type;
    private String content;
    private String imgUrl;
    private LocalDateTime reg_dt;
    private LocalDateTime mod_dt;



    public BoardResponseDto(NoticeBoard noticeBoard){
        this.noticeId = noticeBoard.getNoticeId();
        this.subject = noticeBoard.getSubject();
        this.content = noticeBoard.getContent();
        this.type = noticeBoard.getType();
        this.imgUrl = noticeBoard.getImgUrl();
        this.reg_dt = noticeBoard.getReg_dt();
        this.mod_dt = noticeBoard.getMod_dt();
    }
}
