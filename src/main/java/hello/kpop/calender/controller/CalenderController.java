package hello.kpop.calender.controller;

import hello.kpop.calender.dto.CalenderDto;
import hello.kpop.calender.service.CalenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CalenderController {

    private final CalenderService calenderService;

    @PostMapping("/calenderSignUp")
    public String signUp(@RequestBody CalenderDto calenderDto) throws Exception {
        calenderService.signUp(calenderDto);
        return "캘린더 등록 완료";
    }

}
