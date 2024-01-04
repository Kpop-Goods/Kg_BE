package hello.kpop.artist.controller;

import hello.kpop.artist.dto.ArtistDto;
import hello.kpop.artist.dto.ArtistResponseDto;
import hello.kpop.artist.dto.SuccessResponseDto;
import hello.kpop.artist.service.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;

    //아티스트 등록
    @PostMapping("/artist")
    public String saveArtist(@RequestBody ArtistDto requestDto) throws Exception {
        artistService.saveArtist(requestDto);
        return "아티스트 등록 완료";
    }

    //아티스트 전체 조회
    @GetMapping("/artist/list")
    public List<ArtistResponseDto> selectArtistList() {
        return artistService.selectArtistList();
    }

    //선택한 아티스트 정보 조회
    @GetMapping("/artist/one/{id}")
    public ArtistResponseDto selectArtistOne(@PathVariable Long id) {
        return artistService.selectArtistOne(id);
    }

    //선택한 아티스트 정보 수정
    @PutMapping("/artist/{id}")
    public ArtistResponseDto updateArtist(@PathVariable Long id, @RequestBody ArtistDto requestDto) throws Exception {
        return artistService.updateArtist(id, requestDto);
    }

    //선택한 아티스트 삭제
    @DeleteMapping("/artist/{id}")
    public SuccessResponseDto deleteArtist(@PathVariable Long id) throws Exception {
        return artistService.deleteArtist(id);
    }
}
