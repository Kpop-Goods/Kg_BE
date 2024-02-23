package hello.kpop.goods.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GoodsRequestDto {


    private Long id;
    private String goods_name;
    private String desc;
    private String imageUrl;
    private String userId;



}
