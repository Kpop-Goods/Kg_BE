package hello.kpop.socialing.controller;

import hello.kpop.socialing.dto.SocialingDto;
import hello.kpop.socialing.service.SocialingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SocialingController {

    private final SocialingService socialingService;

    @PostMapping("/socialSignUp")
    public String signUp(@RequestBody SocialingDto socialingDto) throws Exception {
        socialingService.signUp(socialingDto);
        return "소셜링 등록 완료";
    }
}
