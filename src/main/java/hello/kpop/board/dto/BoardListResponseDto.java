package hello.kpop.board.dto;


import hello.kpop.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class BoardListResponseDto{


    private Long noticeId;
    private String subject;
    private User userId;
    private LocalDateTime createdAt;





    public  BoardListResponseDto(NoticeBoard noticeBoard){
        this.noticeId = noticeBoard.getNoticeId();
        this.userId=noticeBoard.getUserId();
        this.subject=noticeBoard.getSubject();
        this.createdAt = noticeBoard.getCreatedAt();

    }
}
