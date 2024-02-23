package hello.kpop.board.dto;

import hello.kpop.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class NoticeBoard extends Base{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeId; //공지사항 게시글 번호

    private String type; //게시글 종류??

    private String password; // 게시판 비밀번호

    @Column(length = 50, nullable = false)
    private String subject; //제목

    @Column(length = 200,nullable = false)
    private String content; //내용

    @Column(length = 15)
    private String imgUrl; //이미지

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int view; //조회수 null은 불가 기본값은 0으로시작

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="userid")
    private User userId; //유저 아이디

    //request 정보를 가져와 entity 만들 때 사용
    public NoticeBoard(BoardRequestDto requestDto){
        this.type=requestDto.getType();
        this.password =requestDto.getPassword();
        this.subject=requestDto.getSubject();
        this.content=requestDto.getContent();
        this.imgUrl=requestDto.getImgUrl();
    }


    // 수정시 사용할 매서드
    public void modifyBoardInfo(BoardRequestDto requestDto){

        this.type=requestDto.getType();
        this.password =requestDto.getPassword();
        this.subject=requestDto.getSubject();
        this.content=requestDto.getContent();
        this.imgUrl=requestDto.getImgUrl();
    }





}
