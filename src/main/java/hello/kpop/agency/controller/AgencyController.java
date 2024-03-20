package hello.kpop.agency.controller;

import hello.kpop.agency.Agency;
import hello.kpop.agency.dto.AgencyDto;
import hello.kpop.agency.dto.AgencyResponseDto;
import hello.kpop.agency.errorHandler.AgencyResponseMessage;
import hello.kpop.agency.repository.AgencyRepository;
import hello.kpop.agency.service.AgencyService;
import hello.kpop.place.dto.SuccessResponseDto;
import hello.kpop.place.errorHandler.DefaultRes;
import hello.kpop.place.errorHandler.StatusCode;
import hello.kpop.user.Role;
import hello.kpop.user.User;
import hello.kpop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AgencyController {

    private final AgencyRepository agencyRepository;
    private final AgencyService agencyService;
    private final UserRepository userRepository;

    //소속사 등록
    @PostMapping("/agency")
    public ResponseEntity<AgencyResponseDto> saveAgency(@RequestBody AgencyDto requestDto, Authentication authentication) throws Exception {

        //토큰이 없는 경우 실행, 즉 로그아웃인 상태에 실행
        if(authentication == null) {
            return new ResponseEntity(DefaultRes.res(StatusCode.UNAUTHORIZED, AgencyResponseMessage.UNAUTHORIZED_AGENCY_REGISTER), HttpStatus.UNAUTHORIZED);
        }

        //소속사 중복 등록 방지
        if(agencyRepository.findByAgencyName(requestDto.getAgencyName()).isPresent()) {
            return new ResponseEntity(DefaultRes.res(StatusCode.CONFLICT, AgencyResponseMessage.OVERLAP_AGENCY), HttpStatus.CONFLICT);
        }

        //소속사 카테고리 코드가 입력되지 않았을 시 실행
        if(requestDto.getAgencyCd() == null) {
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, AgencyResponseMessage.AGENCY_CATEGORY_NOT_ENTERED), HttpStatus.BAD_REQUEST);
        }

        //소속사 이름이 입력되지 않았을 시 실행
        if(requestDto.getAgencyName() == null) {
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, AgencyResponseMessage.AGENCY_NAME_NOT_ENTERED), HttpStatus.BAD_REQUEST);
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByUserEmail(userDetails.getUsername()).orElse(null);

        if (user.getUserType() == Role.ADMIN) {
            //ADMIN 시 실행
            requestDto.updateRegId(user.getNickname());
            log.info("유저닉네임:" + user.getNickname());
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, AgencyResponseMessage.AGENCY_REGISTER_SUCCESS, agencyService.saveAgency(requestDto)),HttpStatus.OK);
        } else {
            //ADMIN이 아닐 시 실행
            return new ResponseEntity(DefaultRes.res(StatusCode.UNAUTHORIZED, AgencyResponseMessage.UNAUTHORIZED_AGENCY_REGISTER), HttpStatus.UNAUTHORIZED);
        }
    }

    //소속사 전체 조회 (아티스트 포함됨)
    @GetMapping("/agency/list")
    public List<AgencyResponseDto> selectAgencyList() {
        return agencyService.selectAgencyList();
    }

    //선택한 소속사와 해당 소속사 아티스트 조회
    @GetMapping("/agency/{agencyId}/artists")
    public ResponseEntity<AgencyResponseDto> getArtistsByAgency(@PathVariable Long agencyId) {
        Optional<Agency> agencyOptional = agencyRepository.findById(agencyId);

        //소속사 ID가 일치하는 게 없을 시 실행
        if(!(agencyRepository.findById(agencyId).isPresent())) {
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, AgencyResponseMessage.NOT_FOUND_AGENCY_ONE), HttpStatus.BAD_REQUEST);
        }

        //삭제된 데이터를 조회했을 시 실행
        Agency agency = agencyOptional.get();
        String delYN = agency.getDelYN();

        if(delYN != null && delYN.equals("Y")) {
            return new ResponseEntity(DefaultRes.res(StatusCode.NOT_FOUND, AgencyResponseMessage.DELETED_AGENCY), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(DefaultRes.res(StatusCode.OK, AgencyResponseMessage.FOUND_AGENCY_ONE_SUCCESS, agencyService.selectArtistsByAgencyOne(agencyId)),HttpStatus.OK);
    }

    //선택한 소속사 정보 수정
    @PutMapping("/agency/{agencyId}")
    public ResponseEntity<AgencyResponseDto> updateAgency(@PathVariable Long agencyId, @RequestBody AgencyDto requestDto, Authentication authentication) throws Exception {

        //토큰이 없는 경우 실행, 즉 로그아웃인 상태에 실행
        if(authentication == null) {
            return new ResponseEntity(DefaultRes.res(StatusCode.UNAUTHORIZED, AgencyResponseMessage.UNAUTHORIZED_AGENCY_UPDATE), HttpStatus.UNAUTHORIZED);
        }

        Optional<Agency> agencyOptional = agencyRepository.findById(agencyId);

        //소속사 ID가 일치하는 게 없을 시 실행
        if(!(agencyRepository.findById(agencyId).isPresent())) {
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, AgencyResponseMessage.UPDATE_AGENCY_FAIL), HttpStatus.BAD_REQUEST);
        }

        //삭제된 데이터를 조회했을 시 실행
        Agency agency = agencyOptional.get();
        String delYN = agency.getDelYN();

        if(delYN != null && delYN.equals("Y")) {
            return new ResponseEntity(DefaultRes.res(StatusCode.NOT_FOUND, AgencyResponseMessage.DELETED_AGENCY), HttpStatus.NOT_FOUND);
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByUserEmail(userDetails.getUsername()).orElse(null);

        //관리자만 수정가능
        if(agency.getRegId().equals(user.getNickname())) {
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, AgencyResponseMessage.UPDATE_AGENCY_SUCCESS, agencyService.updateAgency(agencyId, requestDto)),HttpStatus.OK);
        } else {
            return new ResponseEntity(DefaultRes.res(StatusCode.UNAUTHORIZED, AgencyResponseMessage.UNAUTHORIZED_AGENCY_UPDATE), HttpStatus.UNAUTHORIZED);
        }
    }

    //선택한 소속사 삭제
    @DeleteMapping("/agency/{agencyId}")
    public ResponseEntity<SuccessResponseDto> deleteAgency(@PathVariable Long agencyId, Authentication authentication) throws Exception {

        //토큰이 없는 경우 실행, 즉 로그아웃인 상태에 실행
        if(authentication == null) {
            return new ResponseEntity(DefaultRes.res(StatusCode.UNAUTHORIZED, AgencyResponseMessage.UNAUTHORIZED_AGENCY_DELETE), HttpStatus.UNAUTHORIZED);
        }

        //소속사 ID가 일치하는 게 없을 시 실행
        if(!(agencyRepository.findById(agencyId).isPresent())) {
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, AgencyResponseMessage.DELETE_AGENCY_FAIL), HttpStatus.BAD_REQUEST);
        }

        Optional<Agency> agencyOptional = agencyRepository.findById(agencyId);
        Agency agency = agencyOptional.get();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByUserEmail(userDetails.getUsername()).orElse(null);

        //관리자만 수정가능
        if(agency.getRegId().equals(user.getNickname())) {
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, AgencyResponseMessage.DELETE_AGENCY_SUCCESS, agencyService.deleteAgency(agencyId)),HttpStatus.OK);
        } else {
            return new ResponseEntity(DefaultRes.res(StatusCode.UNAUTHORIZED, AgencyResponseMessage.UNAUTHORIZED_AGENCY_DELETE), HttpStatus.UNAUTHORIZED);
        }
    }
}
