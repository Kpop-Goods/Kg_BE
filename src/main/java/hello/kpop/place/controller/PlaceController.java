package hello.kpop.place.controller;

import hello.kpop.artist.dto.SuccessResponseDto;
import hello.kpop.artist.repository.ArtistRepository;
import hello.kpop.place.Place;
import hello.kpop.place.dto.MultiResponseDto;
import hello.kpop.place.dto.PlaceDto;
import hello.kpop.place.dto.PlaceResponseDto;
import hello.kpop.place.errorHandler.DefaultRes;
import hello.kpop.place.errorHandler.PlaceResponseMessage;
import hello.kpop.place.errorHandler.StatusCode;
import hello.kpop.place.repository.PlaceRepository;
import hello.kpop.place.service.PlaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;
    private final ArtistRepository artistRepository;
    private final PlaceRepository placeRepository;

    //이벤트 장소 등록
    @PostMapping("/place/{artistId}") //추후 파라미터에 로그인 한 유저 정보 필요
    public ResponseEntity<PlaceResponseDto> savePlace(@RequestBody PlaceDto requestDto, @PathVariable Long artistId) throws Exception {

        //이벤트 중복 등록 방지
        if(placeRepository.findByAddress(requestDto.getAddress()).isPresent() && artistRepository.findById(artistId).isPresent()) {
            return new ResponseEntity(DefaultRes.res(StatusCode.CONFLICT, PlaceResponseMessage.OVERLAP_EVENT), HttpStatus.CONFLICT);
        }

        //아티스트 ID가 일치하는 게 없을 시 실행
        if(!(artistRepository.findById(artistId).isPresent())) {
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, PlaceResponseMessage.NOT_FOUND_ARTIST_ID), HttpStatus.BAD_REQUEST);
        }

        //이벤트 카테고리 코드 입력되지 않았을 시 실행
        if(requestDto.getEventCategoryCd() == null) {
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, PlaceResponseMessage.EVENT_CATEGORY_NOT_ENTERED), HttpStatus.BAD_REQUEST);
        }

        //이벤트 시작날짜 입력되지 않았을 시 실행
        if(requestDto.getStartDate() == null) {
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, PlaceResponseMessage.START_DATE_NOT_ENTERED), HttpStatus.BAD_REQUEST);
        }

        //이벤트 마감날짜 입력되지 않았을 시 실행
        if(requestDto.getEndDate() == null) {
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, PlaceResponseMessage.END_DATE_NOT_ENTERED), HttpStatus.BAD_REQUEST);
        }

        //이벤트명 입력되지 않았을 시 실행
        if(requestDto.getEventName() == null) {
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, PlaceResponseMessage.NAME_NOT_ENTERED), HttpStatus.BAD_REQUEST);
        }

        //이벤트 우편번호 입력되지 않았을 시 실행
        if(requestDto.getAddress() == null) {
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, PlaceResponseMessage.ADDRESS_NOT_ENTERED), HttpStatus.BAD_REQUEST);
        }

        //이벤트 지번주소(기본주소) 입력되지 않을 시 실행
        if(requestDto.getStreetAddress() == null) {
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, PlaceResponseMessage.STREET_ADDRESS_NOT_ENTERED), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, PlaceResponseMessage.EVENT_REGISTER_SUCCESS, placeService.savePlace(requestDto, artistId)), HttpStatus.OK);
    }

    //이벤트 장소 전체 조회
    @GetMapping("/place/list")
    public List<PlaceResponseDto> selectPlaceList() {
        return placeService.selectPlaceList();
    }

    //이벤트 장소 상세 조회
    @GetMapping("/place/one/{eventId}")
    public ResponseEntity<PlaceResponseDto> selectPlaceOne(@PathVariable Long eventId) {
        Optional<Place> placeOptional = placeRepository.findById(eventId);

        //이벤트 ID가 일치하는 게 없을 시 실행
        if(!(placeRepository.findById(eventId).isPresent())) {
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, PlaceResponseMessage.NOT_FOUND_PLACE_ONE), HttpStatus.BAD_REQUEST);
        }

        //삭제된 데이터를 조회했을 시 실행
        Place place = placeOptional.get();
        String delYN = place.getDelYN();

        if(delYN != null && delYN.equals("Y")) {
            return new ResponseEntity(DefaultRes.res(StatusCode.NOT_FOUND, PlaceResponseMessage.DELETED_PLACE), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(DefaultRes.res(StatusCode.OK, PlaceResponseMessage.FOUND_PLACE_ONE_SUCCESS, placeService.selectPlaceOne(eventId)), HttpStatus.OK);
    }

    //선택한 이벤트 장소 정보 수정
    @PutMapping("/place/{eventId}") //추후 파라미터에 로그인 한 유저 정보 필요
    public ResponseEntity<PlaceResponseDto> updatePlace(@PathVariable Long eventId, @RequestBody PlaceDto requestDto) throws Exception {
        Optional<Place> placeOptional = placeRepository.findById(eventId);

        //이벤트 ID가 일치하는 게 없을 시 실행
        if(!(placeRepository.findById(eventId).isPresent())) {
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, PlaceResponseMessage.UPDATE_PLACE_FAIL), HttpStatus.BAD_REQUEST);
        }

        //삭제된 데이터를 조회했을 시 실행
        Place place = placeOptional.get();
        String delYN = place.getDelYN();

        if(delYN != null && delYN.equals("Y")) {
            return new ResponseEntity(DefaultRes.res(StatusCode.NOT_FOUND, PlaceResponseMessage.DELETED_PLACE), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(DefaultRes.res(StatusCode.OK, PlaceResponseMessage.UPDATE_PLACE_SUCCESS, placeService.updatePlace(eventId, requestDto)), HttpStatus.OK);
    }

    //선택한 이벤트 장소 삭제
    @DeleteMapping("/place/{eventId}") //추후 파라미터에 로그인 한 유저 정보 필요
    public ResponseEntity<SuccessResponseDto> deletePlace(@PathVariable Long eventId) throws Exception {

        //이벤트 ID가 일치하는 게 없을 시 실행
        if(!(placeRepository.findById(eventId).isPresent())) {
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, PlaceResponseMessage.DELETE_PLACE_FAIL), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, PlaceResponseMessage.DELETE_PLACE_SUCCESS, placeService.deletePlace(eventId)), HttpStatus.OK);
    }

    //장소명(이벤트명), 장소 카테고리 코드, 아티스트코드, 소속사코드 검색했을 시 조회
    @GetMapping("/place/search")
    public ResponseEntity findPlace(
            @RequestParam(value = "eventName", required = false) String eventName,
            @RequestParam(value = "eventCategoryCd", required = false) Integer eventCategoryCd,
            @RequestParam(value = "artistId", required = false) Long artistId,
            @RequestParam(value = "agencyId", required = false) Long agencyId,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        List<PlaceResponseDto> lists = placeService.searchPlace(eventName, eventCategoryCd, artistId, agencyId, startDate, endDate);
        MultiResponseDto<PlaceResponseDto> responseDto = MultiResponseDto.<PlaceResponseDto>builder().data(lists).build();
        return ResponseEntity.ok().body(responseDto);
    }
}
