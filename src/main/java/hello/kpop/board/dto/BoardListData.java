package hello.kpop.board.dto;


import hello.kpop.board.Board;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardListData {

    private Long noticeId;

    private String nickName;

    private String subject;

    private String content;

    private boolean notice_yn;

    private boolean del_yn;

    private LocalDateTime regDt;


    public BoardListData(Board board){
        this.noticeId=board.getNoticeId();
        this.nickName= board.getUser().getNickname();
        this.subject = board.getSubject();
        this.content = board.getContent();
        this.notice_yn = board.isNotice_yn();
        this.del_yn = board.isDel_yn();
        this.regDt = board.getRegDt();
    }

}
