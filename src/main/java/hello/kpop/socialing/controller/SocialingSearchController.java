package hello.kpop.socialing.controller;


import hello.kpop.socialing.common.ListData;
import hello.kpop.socialing.common.ResponseJSONData;
import hello.kpop.socialing.dto.SocialingListData;
import hello.kpop.socialing.dto.SocialingSearchData;
import hello.kpop.socialing.service.SocialingInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("social/search")
public class SocialingSearchController {

    private final SocialingInfoService infoService;
    @GetMapping
    public ResponseJSONData<ListData<SocialingListData>> search( SocialingSearchData searchDto) {

        ListData<SocialingListData> data = infoService.getList(searchDto);

        return new ResponseJSONData<>(data, "검색 완료");
    }

}
