package hello.kpop.agency.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@Slf4j
public class AgencyDto {

    private String agencyCd;
    private String agencyName;
    private int countryCd;
    private LocalDate establishDt;
    private String agencyInfo;
    private String regId;

    public void updateRegId(String regId) {
        this.regId = regId;
        log.info("유저닉네임:" + this.regId);
    }
}
