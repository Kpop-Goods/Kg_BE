package hello.kpop.socialing.controller;


import hello.kpop.socialing.Socialing;
import hello.kpop.socialing.common.ListData;
import hello.kpop.socialing.common.ProcessUtils;
import hello.kpop.socialing.common.ResponseJSONData;
import hello.kpop.socialing.dto.SocialingSearchDto;
import hello.kpop.socialing.exception.BadRequestException;
import hello.kpop.socialing.service.SocialingInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("social/search")
public class SocialingSearchController {

    private final SocialingInfoService infoService;


    @GetMapping
    public ResponseJSONData<ListData<?>> search(SocialingSearchDto searchDto){
        if (!StringUtils.hasText(searchDto.getSkey())) {
            throw new BadRequestException(ProcessUtils.getMessage("NotBlank.skey", "errors"));
        }
        String sopt = searchDto.getSopt();

        if(!StringUtils.hasText(sopt)){
             searchDto.setSopt("ALL");
        }
        ListData<Socialing> listdata = infoService.getList(searchDto);

        return new ResponseJSONData<>(listdata,"검색 완료");
    }




}
