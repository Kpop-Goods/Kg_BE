package hello.kpop.place.controller;

import hello.kpop.place.Place;
import hello.kpop.place.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

    @PostMapping("/placeRegister")
    public String signUp(@RequestBody Place place) throws Exception {
        placeService.signUp(place);
        return "장소 등록 성공";
    }
}
