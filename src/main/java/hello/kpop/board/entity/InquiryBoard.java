package hello.kpop.board.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InquiryBoard extends Base{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inquiryId; //문의 게시판 번호

    @Column(length = 50, nullable = false)
    private String subject; //제목

    @Column(length = 200,nullable = false)
    private String content; // 내용

    @Column(length = 15)
    private String img; //이미지


    //    @ManyToOne(fetch= FetchType.LAZY)
    //    @JoinColumn(name="userNo")
    //    private Member member;

    //리뷰,답변,별점,상품 종류등등 다음 회의때 물어보기( 상품판매,등록이없어서 )

}
