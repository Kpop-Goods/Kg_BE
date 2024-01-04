package hello.kpop.goods.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsResponseDto {


    private Long id;
    private String goods_name;
    private String desc;
    private String imageUrl;

    public GoodsResponseDto(Goods goods) {
        this.id = goods.getId();
        this.goods_name = goods.getGoods_name();
        this.desc = goods.getDesc();
        this.imageUrl= goods.getImageUrl();
    }
}
