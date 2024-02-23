package hello.kpop.socialing.controller;

import hello.kpop.socialing.Socialing;
import hello.kpop.socialing.common.ListData;
import hello.kpop.socialing.common.ResponseJSONData;
import hello.kpop.socialing.dto.SocialViewData;
import hello.kpop.socialing.dto.SocialingRequestDto;
import hello.kpop.socialing.dto.SocialingResponseDto;
import hello.kpop.socialing.dto.SocialingSearchDto;
import hello.kpop.socialing.service.AuthenticationService;
import hello.kpop.socialing.service.SocialingDeleteService;
import hello.kpop.socialing.service.SocialingInfoService;
import hello.kpop.socialing.service.SocialingSaveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/social")
public class SocialingController  {

    private final SocialingSaveService saveService;
    private final SocialingInfoService infoService;
    private final SocialingDeleteService deleteService;
    private final AuthenticationService authenticationService;


    //소셜 등록
    @PostMapping
    public ResponseJSONData<Object> createSocial(@RequestBody @Valid SocialingRequestDto requestDto, BindingResult bindingResult, @RequestHeader("Authorization") String authorizationHeader) {
        // 토큰 추출
//        String token = extractToken(authorizationHeader);
//        System.out.println(token);
//        if (!authenticationService.isUserLoggedIn(token)) {
//            // 사용자가 인증되어 있지 않은 경우
//            return new ResponseJSONData<>(false,HttpStatus.UNAUTHORIZED,null, "회원 전용 서비스입니다.");
//        }
        SocialingResponseDto socialingResponseDto = saveService.createSocial(requestDto, bindingResult);
        return new ResponseJSONData<>(socialingResponseDto, "등록 성공");
    }

    private String extractToken(String authorizationHeader) {
        // "Bearer <token>" 형태의 헤더에서 토큰 부분 추출
        return authorizationHeader.replace("Bearer ", "");
    }

    //소셜 전체 조회
    @GetMapping("/list")
    public ListData<Socialing> findSocialList(SocialingSearchDto searchDto) {
        ListData<Socialing> page = infoService.getList(searchDto);
        return new ListData<>(page.getContent(), page.getPagination());
    }

    //소셜 단일 조회
    @GetMapping("/view/{id}")
    public ResponseJSONData<Object> findSocial(@PathVariable Long id) {
        infoService.socialingViewCount(id); // 조회수 업데이트
        SocialViewData socialViewData = infoService.findSocialAndList(id);
        return new ResponseJSONData<>(socialViewData, "소셜링 단일 조회 성공");
    }

    //소셜 수정
    @PutMapping("/{id}")
    public ResponseJSONData<?> updateSocial(@PathVariable Long id, @RequestBody SocialingRequestDto socialingDto) {
        SocialingResponseDto socialingResponseDto = saveService.updateSocial(id, socialingDto);
        return new ResponseJSONData<>(socialingResponseDto, "수정이 완료 되었습니다.");
    }

    // 소셜 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSocial(@PathVariable Long id) {
        deleteService.deleteSocial(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}




