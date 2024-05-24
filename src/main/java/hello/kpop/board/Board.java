package hello.kpop.board;

import hello.kpop.board.dto.BoardData;
import hello.kpop.image.StringListConverter;
import hello.kpop.socialing.common.entitiy.BaseMember;
import hello.kpop.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Board extends BaseMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="notice_id")
    private Long noticeId; //공지사항 게시글 번호


    @Column(length = 50, nullable = false)
    private String subject; //제목

    @Column(length = 200,nullable = false)
    private String content; //내용

    @Column(columnDefinition = "TINYINT(1)")
    private boolean notice_yn; // 노츨 여부

    @Column(columnDefinition = "TINYINT(1)")
    private boolean del_yn; // 삭제 여부

    //@Column
    //private String image_url -- 이미지

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="user_fk")
    private User user; //유저 아이디

    @Convert(converter = StringListConverter.class)
    @Column(name = "image_urls")
    private List<String> imageUrls;

    // 이미지 URL 리스트를 추가하는 메서드
    public void addImageUrl(String imageUrl) {
        this.imageUrls.add(imageUrl);
    }

    // 이미지 URL 리스트를 설정하는 메서드
    public void updateImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }


    // 수정시 사용할 매서드
    public void modifyBoardInfo(BoardData data){

        this.subject = data.getSubject();
        this.content  =data.getContent();
        this.notice_yn=data.isNotice_yn();
        this.del_yn=data.isDel_yn();

    }


    public void deleteStatus() {
        this.del_yn = true;
    }

    public void undeleteStatus() {
        this.del_yn = false;
    }









}
