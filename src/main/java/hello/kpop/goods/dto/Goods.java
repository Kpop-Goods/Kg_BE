package hello.kpop.goods.dto;


import hello.kpop.image.StringListConverter;
import hello.kpop.socialing.common.entitiy.Base;
import lombok.*;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "goods")
public class Goods extends Base {

    //상품 아이디
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goods_id")
    private Long goodsId;

    //아티스트 코드
    @Column(name = "artist_cd", length=100, nullable = false)
    private String artistCd ;

    //상품 카테고리 코드
    @Column(name = "goods_category_cd", nullable = false)
    private Integer goodsCategoryCd;

    //굿즈명
    @Column(name = "goods_name", length = 500, nullable = false)
    private String goodsName;

    //내용
    @Lob
    @Column(name = "goods_content", columnDefinition = "TEXT")
    private String goodsContent;

    //원본 링크
    @Column(name = "goods_link", length = 4000)
    private String goodsLink;

    //조회수
    @Column(name = "count")
    private Integer count;

    //삭제여부
    @Column(name = "del_yn", length = 1, nullable = false)
    private String delYN;

    //등록ID
    @Column(name = "reg_id")
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


}
