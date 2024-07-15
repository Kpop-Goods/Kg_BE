package hello.kpop.goods.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class GoodsResponseDto {

    private Long goodsId;
    private String goodsCategoryCd;
    private String goodsName;
    private String goodsPrice;
    private String goodsContent;
    private LocalDate startDate;
    private LocalDate endDate;
    private String platform;
    private String snsId;
    private int count;
    private String delYN;
    private String regId;
    private LocalDateTime regDt;
    private LocalDateTime modDt;
    private Long artistId;
    private Long agencyId;

    public GoodsResponseDto(Goods entity) {
        this.goodsId = entity.getGoodsId();
        this.goodsCategoryCd = entity.getGoodsCategoryCd();
        this.goodsName = entity.getGoodsName();
        this.goodsPrice = entity.getGoodsPrice();
        this.goodsContent = entity.getGoodsContent();
        this.startDate = entity.getStartDate();
        this.endDate = entity.getEndDate();
        this.platform = entity.getPlatform();
        this.snsId = entity.getSnsId();
        this.count = entity.getCount();
        this.delYN = entity.getDelYN();
        this.regId = entity.getRegId();
        this.regDt = entity.getRegDt();
        this.modDt = entity.getMod_dt();
        this.artistId = entity.getArtist().getArtistId();
        this.agencyId = entity.getArtist().getAgency().getAgencyId();
    }

}
