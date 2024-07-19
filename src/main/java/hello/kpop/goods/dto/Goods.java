package hello.kpop.goods.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import hello.kpop.agency.Agency;
import hello.kpop.artist.Artist;
import hello.kpop.image.StringListConverter;
import hello.kpop.socialing.common.entitiy.Base;
import lombok.*;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "goods")
@Builder
public class Goods extends Base {

    //상품 아이디
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goods_id")
    private Long goodsId;

    //아티스트 코드
    @JsonIgnore
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "artist_fk")
    private Artist artist; //fk(=Artist_pk)

    //소속사 코드
    /*@JsonIgnore
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "agency_fk")
    private Agency agency; //fk(=Agency_pk)*/

    //상품 카테고리 코드
    /*인형-01000 / 키링-02000 / 응원봉-03000 / 슬로건-04000 / 패션-05000 / 포토카드-06000 / 포토북-07000 / 컵홀더-08000 / 텀블러-09000 / 스티커-10000 / 잡화-11000 / 지류굿즈-12000*/
    @Column(name = "goods_category_cd", length = 100, nullable = false)
    private String goodsCategoryCd;

    //굿즈명
    @Column(name = "goods_name", length = 500, nullable = false)
    private String goodsName;

    //가격
    @Column(name = "goods_price", length = 100, nullable = false)
    private String goodsPrice;

    //내용
    @Column(name = "goods_content", columnDefinition = "TEXT")
    private String goodsContent;

    //시작 날짜
    @Column(name = "start_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate startDate;

    //종료 날짜
    @Column(name = "end_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate endDate;

    //플랫폼(예:트위터, 인스타그램 등)
    @Column(name = "platform", length = 100)
    private String platform;

    //외부 아이디(예: 트위터 아이디, 인스타그램 아이디)
    @Column(name = "sns_id", length = 1000)
    private String snsId;

    //조회수
    @Column(name = "count", columnDefinition = "integer default 0")
    private int count;

    //삭제여부
    @Column(name = "del_yn", length = 1)
    private String delYN;

    //등록 아이디
    @Column(name = "reg_id", length = 100)
    private String regId;

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

    //아티스트
    public Goods(GoodsRequestDto requestDto, /*Agency agency,*/ Artist artist) {
        this.goodsCategoryCd = requestDto.getGoodsCategoryCd();
        this.goodsName = requestDto.getGoodsName();
        this.goodsPrice = requestDto.getGoodsPrice();
        this.goodsContent = requestDto.getGoodsContent();
        this.startDate = requestDto.getStartDate();
        this.endDate = requestDto.getEndDate();
        this.platform = requestDto.getPlatform();
        this.snsId = requestDto.getSnsId();
        this.regId = requestDto.getRegId();
//        this.agency = agency;
        this.artist = artist;
    }

    public void update(GoodsRequestDto requestDto) {
        this.goodsCategoryCd = requestDto.getGoodsCategoryCd();
        this.goodsName = requestDto.getGoodsName();
        this.goodsPrice = requestDto.getGoodsPrice();
        this.goodsContent = requestDto.getGoodsContent();
        this.startDate = requestDto.getStartDate();
        this.endDate = requestDto.getEndDate();
        this.platform = requestDto.getPlatform();
        this.snsId = requestDto.getSnsId();
    }

    public void updateDelYN(String delYN) {
        this.delYN = delYN;
    }
}
