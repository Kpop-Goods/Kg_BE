package hello.kpop.artist.controller;

import hello.kpop.agency.repository.AgencyRepository;
import hello.kpop.artist.Artist;
import hello.kpop.artist.dto.ArtistDto;
import hello.kpop.artist.dto.ArtistResponseDto;
import hello.kpop.artist.dto.SuccessResponseDto;
import hello.kpop.artist.repository.ArtistRepository;
import hello.kpop.artist.service.ArtistService;
import hello.kpop.place.common.MultiResponseDto;
import hello.kpop.place.common.DefaultRes;
import hello.kpop.artist.errorHandler.ArtistResponseMessage;
import hello.kpop.place.common.PageResponseDto;
import hello.kpop.place.common.StatusCode;
import hello.kpop.user.Role;
import hello.kpop.user.User;
import hello.kpop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;
    private final ArtistRepository artistRepository;
    private final AgencyRepository agencyRepository;
    private final UserRepository userRepository;

    //아티스트 등록
    @PostMapping("/artist/{agencyId}")
    public ResponseEntity<ArtistResponseDto> saveArtist(@RequestBody ArtistDto requestDto, @PathVariable Long agencyId, Authentication authentication) throws Exception {

        //토큰이 없는 경우 실행, 즉 로그아웃인 상태에 실행
        if(authentication == null) {
            return new ResponseEntity(DefaultRes.res(StatusCode.UNAUTHORIZED, ArtistResponseMessage.UNAUTHORIZED_ARTIST_REGISTER), HttpStatus.UNAUTHORIZED);
        }

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

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByUserEmail(userDetails.getUsername()).orElse(null);

        if (user.getUserType() == Role.ADMIN) {
            requestDto.updateRegId(user.getNickname());
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ArtistResponseMessage.ARTIST_REGISTER_SUCCESS, artistService.saveArtist(requestDto, agencyId)), HttpStatus.OK);
        } else {
            return new ResponseEntity(DefaultRes.res(StatusCode.UNAUTHORIZED, ArtistResponseMessage.UNAUTHORIZED_ARTIST_REGISTER), HttpStatus.UNAUTHORIZED);
        }
    }

    //아티스트 전체 조회
//    @GetMapping("/artist/list")
//    public List<ArtistResponseDto> selectArtistList() {
//        return artistService.selectArtistList();
//    }

    //페이징 + 아티스트 전체 조회
    @GetMapping("/artist/list")
    public ResponseEntity<ArtistResponseDto> pageArtist(@RequestParam(required = false, defaultValue = "0", value = "page") int page,
                                                        @RequestParam(required = false, defaultValue = "16", value = "size") int size) {
        //page, size가 음수일 경우
        if(page < 0 || size <= 0) {
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, ArtistResponseMessage.NON_NEGATIVE_VALUES), HttpStatus.BAD_REQUEST);
        }

        Page<Artist> result = artistService.pageArtistList(page -1, size);
        List<Artist> list = result.getContent();

        return new ResponseEntity(new PageResponseDto<>(list, result), HttpStatus.OK);
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
    @PutMapping("/artist/{artistId}")
    public ResponseEntity<ArtistResponseDto> updateArtist(@PathVariable Long artistId, @RequestBody ArtistDto requestDto, Authentication authentication) throws Exception {

        //토큰이 없는 경우 실행, 즉 로그아웃인 상태에 실행
        if(authentication == null) {
            return new ResponseEntity(DefaultRes.res(StatusCode.UNAUTHORIZED, ArtistResponseMessage.UNAUTHORIZED_ARTIST_UPDATE), HttpStatus.UNAUTHORIZED);
        }

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

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByUserEmail(userDetails.getUsername()).orElse(null);

        //관리자만 수정 가능
        if(artist.getRegId().equals(user.getNickname())) {
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ArtistResponseMessage.UPDATE_ARTIST_SUCCESS, artistService.updateArtist(artistId, requestDto)), HttpStatus.OK);
        } else {
            return new ResponseEntity(DefaultRes.res(StatusCode.UNAUTHORIZED, ArtistResponseMessage.UNAUTHORIZED_ARTIST_UPDATE), HttpStatus.UNAUTHORIZED);
        }
    }

    //선택한 아티스트 삭제
    @DeleteMapping("/artist/{artistId}")
    public ResponseEntity<SuccessResponseDto> deleteArtist(@PathVariable Long artistId, Authentication authentication) throws Exception {

        //토큰이 없는 경우 실행, 즉 로그아웃인 상태에 실행
        if(authentication == null) {
            return new ResponseEntity(DefaultRes.res(StatusCode.UNAUTHORIZED, ArtistResponseMessage.UNAUTHORIZED_ARTIST_DELETE), HttpStatus.UNAUTHORIZED);
        }

        Optional<Artist> artistOptional = artistRepository.findById(artistId);
        Artist artist = artistOptional.get();

        //아티스트 ID가 일치하는 게 없을 시 실행
        if(!(artistRepository.findById(artistId).isPresent())) {
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, ArtistResponseMessage.DELETE_ARTIST_FAIL), HttpStatus.BAD_REQUEST);
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByUserEmail(userDetails.getUsername()).orElse(null);

        if(artist.getRegId().equals(user.getNickname())) {
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ArtistResponseMessage.DELETE_ARTIST_SUCCESS, artistService.deleteArtist(artistId)), HttpStatus.OK);
        } else {
            return new ResponseEntity(DefaultRes.res(StatusCode.UNAUTHORIZED, ArtistResponseMessage.UNAUTHORIZED_ARTIST_DELETE), HttpStatus.UNAUTHORIZED);
        }

    }

    //아티스트 명 검색했을 시 조회
    @GetMapping("/artist/search")
    public ResponseEntity findArtist(@RequestParam(value = "artistName", required = false) String artistName) {

        List<ArtistResponseDto> lists = artistService.searchArtist(artistName);
        MultiResponseDto<ArtistResponseDto> responseDto = MultiResponseDto.<ArtistResponseDto>builder().data(lists).build();
        return ResponseEntity.ok().body(responseDto);
    }
}
