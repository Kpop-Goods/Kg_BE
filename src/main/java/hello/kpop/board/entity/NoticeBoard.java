package hello.kpop.board.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeBoard extends Base{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeId; //공지사항 게시글 번호

    private String type; //게시글 종류??

    @Column(length = 50, nullable = false)
    private String subject; //제목

    @Column(length = 200,nullable = false)
    private String content; //내용

    @Column(length = 15)
    private String img; //이미지

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int view; //조회수 null은 불가 기본값은 0으로시작


//    @ManyToOne(fetch= FetchType.LAZY)
//    @JoinColumn(name="userNo")
//    private Member member;





}
