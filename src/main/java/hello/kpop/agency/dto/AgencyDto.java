package hello.kpop.agency.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class AgencyDto {

    private String agencyCd;
    private String agencyName;
    private int countryCd;
    private LocalDate establishDt;
    private String agencyInfo;
    private String delYN;
    private String regId;
    private String modId;
}
