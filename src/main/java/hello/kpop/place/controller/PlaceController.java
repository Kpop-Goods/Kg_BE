package hello.kpop.place.controller;

import hello.kpop.artist.dto.ArtistDto;
import hello.kpop.artist.dto.ArtistResponseDto;
import hello.kpop.artist.dto.SuccessResponseDto;
import hello.kpop.artist.repository.ArtistRepository;
import hello.kpop.place.Place;
import hello.kpop.place.dto.MultiResponseDto;
import hello.kpop.place.dto.PlaceDetailDto;
import hello.kpop.place.dto.PlaceDto;
import hello.kpop.place.dto.PlaceResponseDto;
import hello.kpop.place.errorHandler.DefaultRes;
import hello.kpop.place.errorHandler.ResponseMessage;
import hello.kpop.place.errorHandler.StatusCode;
import hello.kpop.place.repository.PlaceRepository;
import hello.kpop.place.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.Id;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;
    private final ArtistRepository artistRepository;
    private final PlaceRepository placeRepository;

    //장소 등록
//    @PostMapping("/place/{id}")
//    public String savePlace(@RequestBody PlaceDto requestDto, @PathVariable Long id) {
//        placeService.savePlace(requestDto, id);
//        return "장소 등록 완료";
//    }
    @PostMapping("/place/{id}")
    public ResponseEntity<PlaceResponseDto> savePlace(@RequestBody PlaceDto requestDto, @PathVariable Long id) {

        if(!(artistRepository.findById(id).isPresent())) {
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.REGISTER_FAIL), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.REGISTER_SUCCESS, placeService.savePlace(requestDto,id)), HttpStatus.OK);
    }

    //장소 전체 조회
    @GetMapping("/place/list")
    public List<PlaceResponseDto> selectPlaceList() {
        return placeService.selectPlaceList();
    }

    //선택한 장소 상세 조회
//    @GetMapping("/place/one/{id}")
//    public PlaceResponseDto selectPlaceOne(@PathVariable Long id) {
//        return placeService.selectPlaceOne(id);
//    }
    @GetMapping("/place/one/{id}")
    public ResponseEntity<PlaceResponseDto> selectPlaceOne(@PathVariable Long id) {
        if(!(placeRepository.findById(id).isPresent())) {
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.NOT_FOUND_PLACE_ONE), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.FOUND_PLACE_ONE_SUCCESS, placeService.selectPlaceOne(id)), HttpStatus.OK);
    }

    //선택한 장소 정보 수정
//    @PutMapping("/place/{id}")
//    public PlaceResponseDto updatePlace(@PathVariable Long id, @RequestBody PlaceDetailDto requestDto) throws Exception {
//        return placeService.updatePlace(id, requestDto);
//    }
    @PutMapping("/place/{id}")
    public ResponseEntity<PlaceResponseDto> updatePlace(@PathVariable Long id, @RequestBody PlaceDetailDto requestDto) throws Exception {

        if(!(placeRepository.findById(id).isPresent())) {
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.UPDATE_PLACE_FAIL), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.UPDATE_PLACE_SUCCESS, placeService.updatePlace(id, requestDto)), HttpStatus.OK);
    }

    //선택한 장소 삭제
//    @DeleteMapping("/place/{id}")
//    public SuccessResponseDto deletePlace(@PathVariable Long id) throws Exception {
//        return placeService.deletePlace(id);
//    }
    @DeleteMapping("/place/{id}")
    public ResponseEntity<SuccessResponseDto> deletePlace(@PathVariable Long id) throws Exception {

        if(!(placeRepository.findById(id).isPresent())) {
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.DELETE_PLACE_FAIL), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.DELETE_PLACE_SUCCESS, placeService.deletePlace(id)), HttpStatus.OK);
    }

    //장소명(이벤트명) 검색했을 시 조회
//    @GetMapping("/place/search")
//    public ResponseEntity searchName(@RequestParam(value = "name", required = false) String name) {
//        List<Place> lists = placeService.searchName(name);
//
//        return new ResponseEntity(new MultiResponseDto<>(lists), HttpStatus.OK);
//    }

    @GetMapping("/place/search")
    public ResponseEntity<List<Place>> searchPlace(@RequestParam(value = "keyword", required = false) String keyword) {
        List<Place> searchResult = placeService.searchPlace(keyword);

        if (searchResult != null && !searchResult.isEmpty()) {
            return new ResponseEntity<>(searchResult, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
