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

    private Long goodsId;
    private String artistCd ;
    private Integer goodsCategoryCd;
    private String goodsName;
    private String goodsContent;
    private String goodsLink;
    private Integer count;
    private String delYN;
    private String regId;

}
