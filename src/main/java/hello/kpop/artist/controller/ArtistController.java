package hello.kpop.artist.controller;

import hello.kpop.agency.repository.AgencyRepository;
import hello.kpop.artist.Artist;
import hello.kpop.artist.dto.ArtistDto;
import hello.kpop.artist.dto.ArtistResponseDto;
import hello.kpop.artist.dto.SuccessResponseDto;
import hello.kpop.artist.repository.ArtistRepository;
import hello.kpop.artist.service.ArtistService;
import hello.kpop.place.dto.MultiResponseDto;
import hello.kpop.place.errorHandler.DefaultRes;
import hello.kpop.artist.errorHandler.ArtistResponseMessage;
import hello.kpop.place.errorHandler.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;
    private final ArtistRepository artistRepository;
    private final AgencyRepository agencyRepository;

    //아티스트 등록
    @PostMapping("/artist/{agencyId}") //추후 파라미터에 로그인 한 유저 정보 필요
    public ResponseEntity<ArtistResponseDto> saveArtist(@RequestBody ArtistDto requestDto, @PathVariable Long agencyId) throws Exception {

        //소속사 ID가 일치하는 게 없을 시 실행
        if(!(agencyRepository.findById(agencyId).isPresent())) {
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, ArtistResponseMessage.NOT_FOUND_AGENCY_ID), HttpStatus.BAD_REQUEST);
        }

        //아티스트 코드 입력되지 않았을 시 실행
        if(requestDto.getArtistCd() == null) {
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, ArtistResponseMessage.ARTIST_CODE_NOT_ENTERED), HttpStatus.BAD_REQUEST);
        }

        //아티스트 명 입력되지 않았을 시 실행
        if(requestDto.getArtistName() == null) {
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, ArtistResponseMessage.ARTIST_NAME_NOT_ENTERED), HttpStatus.BAD_REQUEST);
        }

        //아티스트 설명이 입력되지 않았을 시 실행
        if(requestDto.getComment() == null) {
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, ArtistResponseMessage.ARTIST_COMMENT_NOT_ENTERED), HttpStatus.BAD_REQUEST);
        }

        //아티스트 이미지가 입력되지 않았을 시 실행
//        if(!(imageRepository.findById(imageId).isPresent())) {
//            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.ARTIST_IMAGE_NOT_ENTERED), HttpStatus.BAD_REQUEST);
//        }

        //아티스트 명이 중복되었을 시 실행
        if(artistRepository.findByArtistName(requestDto.getArtistName()).isPresent()) {
            return new ResponseEntity(DefaultRes.res(StatusCode.CONFLICT, ArtistResponseMessage.OVERLAP_ARTIST_NAME), HttpStatus.CONFLICT);
        }

        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ArtistResponseMessage.ARTIST_REGISTER_SUCCESS, artistService.saveArtist(requestDto, agencyId)), HttpStatus.OK);
    }

    //아티스트 전체 조회
    @GetMapping("/artist/list")
    public List<ArtistResponseDto> selectArtistList() {
        return artistService.selectArtistList();
    }

    //선택한 아티스트 정보 조회
    @GetMapping("/artist/one/{artistId}")
    public ResponseEntity<ArtistResponseDto> selectArtistOne(@PathVariable Long artistId) {
        Optional<Artist> artistOptional = artistRepository.findById(artistId);

        //아티스트 ID가 일치하는 게 없을 시 실행
        if(!(artistRepository.findById(artistId).isPresent())) {
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, ArtistResponseMessage.FOUND_ARTIST_ONE_FAIL), HttpStatus.BAD_REQUEST);
        }

        //삭제된 데이터를 조회했을 시 실행
        Artist artist = artistOptional.get();
        String delYN = artist.getDelYN();

        if(delYN != null && delYN.equals("Y")) {
            return new ResponseEntity(DefaultRes.res(StatusCode.NOT_FOUND, ArtistResponseMessage.DELETED_ARTIST), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ArtistResponseMessage.FOUND_ARTIST_ONE_SUCCESS, artistService.selectArtistOne(artistId)), HttpStatus.OK);
    }

    //선택한 아티스트 정보 수정
    @PutMapping("/artist/{artistId}") //추후 파라미터에 로그인 한 유저 정보 필요
    public ResponseEntity<ArtistResponseDto> updateArtist(@PathVariable Long artistId, @RequestBody ArtistDto requestDto) throws Exception {
        Optional<Artist> artistOptional = artistRepository.findById(artistId);

        //아티스트 ID가 일치하는 게 없을 시 실행
        if(!(artistRepository.findById(artistId).isPresent())) {
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, ArtistResponseMessage.UPDATE_ARTIST_FAIL), HttpStatus.BAD_REQUEST);
        }

        //삭제된 데이터를 조회했을 시 실행
        Artist artist = artistOptional.get();
        String delYN = artist.getDelYN();

        if(delYN != null && delYN.equals("Y")) {
            return new ResponseEntity(DefaultRes.res(StatusCode.NOT_FOUND, ArtistResponseMessage.DELETED_ARTIST), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ArtistResponseMessage.UPDATE_ARTIST_SUCCESS, artistService.updateArtist(artistId, requestDto)), HttpStatus.OK);
    }

    //선택한 아티스트 삭제
    @DeleteMapping("/artist/{artistId}") //추후 파라미터에 로그인 한 유저 정보 필요
    public ResponseEntity<SuccessResponseDto> deleteArtist(@PathVariable Long artistId) throws Exception {

        //아티스트 ID가 일치하는 게 없을 시 실행
        if(!(artistRepository.findById(artistId).isPresent())) {
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, ArtistResponseMessage.DELETE_ARTIST_FAIL), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ArtistResponseMessage.DELETE_ARTIST_SUCCESS, artistService.deleteArtist(artistId)), HttpStatus.OK);
    }

    //아티스트 명 검색했을 시 조회
    @GetMapping("/artist/search")
    public ResponseEntity findArtist(@RequestParam(value = "artistName", required = false) String artistName) {

        List<ArtistResponseDto> lists = artistService.searchArtist(artistName);
        MultiResponseDto<ArtistResponseDto> responseDto = MultiResponseDto.<ArtistResponseDto>builder().data(lists).build();
        return ResponseEntity.ok().body(responseDto);
    }
}
