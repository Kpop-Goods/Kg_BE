package hello.kpop.agency.service;

import hello.kpop.agency.Agency;
import hello.kpop.agency.status.DelStatus;
import hello.kpop.agency.dto.AgencyDto;
import hello.kpop.agency.dto.AgencyResponseDto;
import hello.kpop.agency.repository.AgencyRepository;
import hello.kpop.artist.Artist;
import hello.kpop.artist.repository.ArtistRepository;
import hello.kpop.place.common.SuccessResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AgencyService {

    private final AgencyRepository agencyRepository;
    private final ArtistRepository artistRepository;

    //소속사 등록
    @Transactional
    public AgencyResponseDto saveAgency(AgencyDto requestDto) throws Exception{

        //소속사 중복 체크
        if(agencyRepository.findByAgencyName(requestDto.getAgencyName()).isPresent()) {
            throw new Exception("이미 등록된 소속사입니다.");
        }

        Agency agency = new Agency(requestDto);

        //등록 시 삭제여부는 N으로 설정
        agency.updateDelYN(DelStatus.DEFAULT);

        agencyRepository.save(agency);

        return new AgencyResponseDto(agency);
    }

    //소속사 전체 조회
    @Transactional
    public List<AgencyResponseDto> selectAgencyList() {

        // 삭제여부가 "Y"인 데이터 가져옴
        List<Agency> agencies = agencyRepository.findByDelYN("Y");

        List<AgencyResponseDto> responseDtoList;
        if (agencies.isEmpty()) { // "Y"인 데이터가 없을 경우 전체 데이터 조회
            responseDtoList = agencyRepository.findAll().stream().map(AgencyResponseDto::new).toList();
        } else {
            responseDtoList = agencyRepository.findAll()
                    .stream()
                    .filter(agency -> !agencies.contains(agency))
                    .map(agency -> {
                        // Artist의 delYN이 "N"인 것만 필터링
                        List<Artist> filteredArtists = agency.getArtists()
                                .stream()
                                .filter(artist -> "N".equals(artist.getDelYN()))
                                .collect(Collectors.toList());

                        // AgencyResponseDto에 필터링된 Artist 목록을 설정하여 반환
                        AgencyResponseDto dto = new AgencyResponseDto(agency);
                        dto.setArtists(filteredArtists);
                        return dto;
                    })
                    .collect(Collectors.toList());
        }
        return responseDtoList;
    }

    //선택한 소속사와 해당 소속사 아티스트 조회
    @Transactional
    public AgencyResponseDto selectArtistsByAgencyOne(Long agencyId) {
        Agency agency = agencyRepository.findById(agencyId).orElseThrow(
                () -> new IllegalArgumentException("소속사 ID가 존재하지 않습니다."));

        List<AgencyResponseDto> responseDto;

        //만약 해당 소속사가 삭제되었다면("Y"로 표시되어 있다면) 예외를 던짐
        if(agency.getAgencyId().equals(agencyId) && agency.getDelYN().equals("Y")) {
            throw new IllegalArgumentException("삭제된 소속사입니다.");
        }

        // Artist의 delYN이 "N"인 것만 필터링
        List<Artist> filteredArtists = agency.getArtists()
                .stream()
                .filter(artist -> "N".equals(artist.getDelYN()))
                .collect(Collectors.toList());

        // AgencyResponseDto에 필터링된 Artist 목록을 설정하여 반환
        AgencyResponseDto dto = new AgencyResponseDto(agency);
        dto.setArtists(filteredArtists);

        return dto;
    }

    //선택한 소속사 정보 수정
    @Transactional
    public AgencyResponseDto updateAgency(Long agencyId, AgencyDto requestDto) throws Exception {
        Agency agency = agencyRepository.findById(agencyId).orElseThrow(
                () -> new IllegalArgumentException("소속사 ID가 존재하지 않습니다."));

        //만약 해당 소속사가 삭제되었다면("Y"로 표시되어 있다면) 예외를 던짐
        if(agency.getAgencyId().equals(agencyId) && agency.getDelYN().equals("Y")) {
            throw new IllegalArgumentException("삭제된 소속사입니다.");
        }

        agency.update(requestDto);
        return new AgencyResponseDto(agency);
    }

    //선택한 소속사 삭제
    @Transactional
    public SuccessResponseDto deleteAgency(Long agencyId) throws Exception {
        Agency agency = agencyRepository.findById(agencyId).orElseThrow(
                () -> new IllegalArgumentException("소속사 ID가 존재하지 않습니다."));

        agency.updateDelYN(DelStatus.DELETE);

        return new SuccessResponseDto(true);
    }
}
