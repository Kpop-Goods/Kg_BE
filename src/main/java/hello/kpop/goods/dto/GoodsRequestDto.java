package hello.kpop.goods.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GoodsRequestDto {

    private String artistCd ;
    private Integer goodsCategoryCd;
    private String goodsName;
    private String goodsContent;
    private String goodsLink;
    private String regId;

}
