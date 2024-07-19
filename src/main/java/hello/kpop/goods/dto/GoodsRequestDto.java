package hello.kpop.goods.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GoodsRequestDto {

    private String goodsCategoryCd;
    private String goodsName;
    private String goodsPrice;
    private String goodsContent;
    private LocalDate startDate;
    private LocalDate endDate;
    private String platform;
    private String snsId;
    private String regId;
    private Long artistId;
    private Long agencyId;

    public void updateRegId(String regId) {
        this.regId = regId;
    }
}
