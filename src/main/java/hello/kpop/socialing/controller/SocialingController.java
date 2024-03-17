package hello.kpop.socialing.controller;

import hello.kpop.socialing.common.ListData;
import hello.kpop.socialing.common.ResponseJSONData;
import hello.kpop.socialing.dto.SocialViewData;
import hello.kpop.socialing.dto.SocialingData;
import hello.kpop.socialing.dto.SocialingListData;
import hello.kpop.socialing.dto.SocialingSearchDto;
import hello.kpop.socialing.exception.BadRequestException;
import hello.kpop.socialing.service.SocialingDeleteService;
import hello.kpop.socialing.service.SocialingInfoService;
import hello.kpop.socialing.service.SocialingSaveService;
import hello.kpop.socialing.service.UserAuthentication;
import hello.kpop.socialing.validator.SocialingValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/social")
public class SocialingController  {

    private final SocialingSaveService saveService;
    private final SocialingInfoService infoService;
    private final SocialingValidator validator;
    private final SocialingDeleteService deleteService;
    private final UserAuthentication userAuthentication;



    //소셜 전체 조회
    @GetMapping("/list")
    public ListData<SocialingListData> findSocialList(SocialingSearchDto searchDto) {
        ListData<SocialingListData> list = infoService.getList(searchDto);
        return new ListData<>(list.getContent(), list.getPagination());
    }

    //소셜 단일 조회
    @GetMapping("/view/{id}")
    public ResponseJSONData<Object> findSocial(@PathVariable Long id) {
        infoService.socialingViewCount(id); // 조회수 업데이트
        SocialViewData socialViewData = infoService.findSocial(id);
        return new ResponseJSONData<>(socialViewData, "소셜링 단일 조회 성공");
    }
    //소셜 등록
    @PostMapping
    public ResponseJSONData<Object> create(@RequestBody @Valid SocialingData data, Errors errors, Authentication authentication) {
        userAuthentication.auth(authentication);
        saveService.createSocial(data,errors,authentication );
         errorProcess(errors);
        return new ResponseJSONData<>(data,"등록 성공");
    }
    //소셜 수정
    @PutMapping("/{id}")
    public ResponseJSONData<SocialingData> updateSocial(@PathVariable Long id, @RequestBody @Valid SocialingData data, Errors errors , Authentication authentication) {
        userAuthentication.auth(authentication);
        saveService.updateSocial(id, data,errors , authentication);
        errorProcess(errors);
        return new ResponseJSONData<>(data, "수정이 완료 되었습니다.");
    }

    // 소셜 삭제
    @DeleteMapping("/{id}")
    public ResponseJSONData<?> deleteSocial(@PathVariable Long id , Authentication authentication) {
        deleteService.deleteSocial(id,authentication);
        return new ResponseJSONData<>(null , "삭제가 완료되었습니다.");
    }

    private void errorProcess(Errors errors){
        if(errors.hasErrors()){
            throw new BadRequestException(errors);
        }
    }





}




