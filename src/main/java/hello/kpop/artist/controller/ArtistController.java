package hello.kpop.artist.controller;

import hello.kpop.artist.dto.ArtistDto;
import hello.kpop.artist.service.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;

    @PostMapping("/artistSignUp")
    public String signUp(@RequestBody ArtistDto artistDto) throws Exception {
        artistService.signUp(artistDto);
        return "아티스트 등록 완료";
    }

}
