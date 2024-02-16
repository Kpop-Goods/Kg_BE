package hello.kpop.goods.dto;


import hello.kpop.board.dto.Base;
import hello.kpop.user.User;
import lombok.*;

import jakarta.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Goods extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 굿즈 번호

    @Column(length=65, nullable = false)
    private String goods_artistCode ; //아티스트 코드(조인필요??)

    @Column(length = 50, nullable = false)
    private String goods_name; // 굿즈 명

    @Lob
    @Column(name = "description" ,length = 200)
    private String desc; //굿즈 설명

    @Column(length = 100)
    private String  imageUrl; //이미지 주소

    @Column(nullable = false)
    private String sns_id; //sns 외부 아이디??


    private int heart;


       @ManyToOne(fetch= FetchType.LAZY)
       @JoinColumn(name="userId")
       private User userId;
       


    public void update(Goods goods){
           this.goods_name = goods.goods_name;
           this.desc = goods.desc;
           this.imageUrl = goods.imageUrl;


       }

}
