package hello.kpop.goods.entity;


import hello.kpop.board.entity.Base;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Goods extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 굿즈 번호

    @Column(length=65, nullable = false)
    private String goods_artistCode ; //아티스트 코드

    @Column(length = 50, nullable = false)
    private String goods_name; // 굿즈 명

    @Lob
    @Column(name = "description" ,length = 150)
    private String desc; //굿즈 설명

    @Column(length = 100)
    private String  imageUrl; //이미지 주소

    @Column(nullable = false)
    private String sns_id; //sns 아이디??



    // 가격 찜개수 총 판매량 제외

    //    @ManyToOne(fetch= FetchType.LAZY)
    //    @JoinColumn(name="userId")
    //   private User user;

}
