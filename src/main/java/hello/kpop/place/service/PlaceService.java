package hello.kpop.place.service;

import hello.kpop.agency.status.DelStatus;
import hello.kpop.artist.Artist;
import hello.kpop.artist.dto.SuccessResponseDto;
import hello.kpop.artist.repository.ArtistRepository;
import hello.kpop.place.Place;
import hello.kpop.place.PlaceSpecification;
import hello.kpop.place.dto.PlaceDto;
import hello.kpop.place.dto.PlaceResponseDto;
import hello.kpop.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final ArtistRepository artistRepository;

    //이벤트/장소 등록
    @Transactional
    public PlaceResponseDto savePlace(PlaceDto requestDto, Long artistId) throws Exception{

        //이벤트/장소 중복 체크
        if(placeRepository.findByAddress(requestDto.getAddress()).isPresent() && artistRepository.findById(artistId).isPresent()) {
            throw new Exception("이미 등록된 이벤트 입니다.");
        }

        Artist artist = artistRepository.findById(artistId).orElseThrow(
                () -> new IllegalArgumentException("아티스트 ID가 존재하지 않습니다."));

        Place place = new Place(requestDto, artist);

        //등록 시 삭제여부는 N으로 설정
//        place.setDelYN("N");
        place.updateDelYN(DelStatus.DEFAULT);

        placeRepository.save(place);

        return new PlaceResponseDto(place);
    }

    //이벤트/장소 전체 조회
    @Transactional
    public List<PlaceResponseDto> selectPlaceList() {

        //삭제여부 "Y"인 데이터 가져옴
        List<Place> places = placeRepository.findByDelYN("Y");

        if(places.isEmpty()) { //"Y"인 데이터가 없을 경우 전체 데이터 조회
            return placeRepository.findAll().stream().map(PlaceResponseDto::new).toList();
        } else {
            //"Y"인 데이터를 제외한 후 반환
            List<PlaceResponseDto> responseDtoList = placeRepository.findAll()
                    .stream()
                    .filter(place -> !places.contains(place))
                    .map(PlaceResponseDto::new)
                    .collect(Collectors.toList());
            return responseDtoList;
        }
    }

    //선택한 이벤트/장소 상세 조회
    @Transactional
    public PlaceResponseDto selectPlaceOne(Long id) {
        Place place = placeRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("장소 ID가 존재하지 않습니다."));

        //만약 해당 장소가 삭제되었다면("Y"로 표시되어 있다면) 예외를 던짐
        if(place.getEventId().equals(id) && place.getDelYN().equals("Y")) {
            throw new IllegalArgumentException("삭제된 장소입니다.");
        }

        //조회수 +1
        int currentCount = place.getCount();
        place.setCount(currentCount + 1);

        return new PlaceResponseDto(place);
    }

    //선택한 이벤트/장소 정보 수정
    @Transactional
    public PlaceResponseDto updatePlace(Long id, PlaceDto requestDto) throws Exception {
        Place place = placeRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("장소 ID가 존재하지 않습니다."));

        //만약 해당 장소가 삭제되었다면("Y"로 표시되어 있다면) 예외를 던짐
        if(place.getEventId().equals(id) && place.getDelYN().equals("Y")) {
            throw new IllegalArgumentException("삭제된 장소입니다.");
        }

        place.update(requestDto);
        return new PlaceResponseDto(place);
    }

    //선택한 이벤트/장소 삭제
    @Transactional
    public SuccessResponseDto deletePlace(Long id) throws Exception {
        Place place = placeRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("장소 ID가 존재하지 않습니다."));

//        place.setDelYN("Y");
        place.updateDelYN(DelStatus.DELETE);

        return new SuccessResponseDto(true);
    }

    //이벤트명, 장소 카테고리 코드, 아티스트코드, 소속사코드 검색했을 시 조회
    @Transactional
    public List<PlaceResponseDto> searchPlace(String eventName, Integer eventCategoryCd, Long artistId, Long agencyId,
                                              LocalDate startDate,
                                              LocalDate endDate) {
        Specification<Place> spec = (root, query, criteriaBuilder) -> null;
        Specification<PlaceResponseDto> specification = (root, query, criteriaBuilder) -> null;

        if (eventName != null) {
            spec = spec.and(PlaceSpecification.equalEventName(eventName));
        }
        if (eventCategoryCd != null) {
            spec = spec.and(PlaceSpecification.equalEventCategoryCode(eventCategoryCd));
        }
        if (artistId != null) {
            spec = spec.and(PlaceSpecification.equalArtist(artistId));
        }
        if (agencyId != null) {
            spec = spec.and(PlaceSpecification.equalAgency(agencyId));
        }
        if (startDate != null && endDate == null) {//검색 기준 : 이벤트 시작날짜
            spec = spec.and(PlaceSpecification.onlyStartDate(startDate));
        }
        if (endDate != null && startDate == null) {//검색 기준 : 이벤트 마감날짜
            spec = spec.and(PlaceSpecification.onlyEndDate(endDate));
        }
        if (startDate != null && endDate != null) {//검색 기준 : 이벤트 시작날짜
            spec = spec.and(PlaceSpecification.betweenStartDate(startDate, endDate));
        }

        // 'delYN'이 'Y'인 데이터를 제외하기 위한 스펙 추가
        spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("delYN"), "Y"));

//        log.info("service : {}", spec);
//        log.info("service2 : {}", placeRepository.findAll(spec).stream().map(PlaceResponseDto::new).toList());
        return placeRepository.findAll(spec).stream().map(PlaceResponseDto::new).toList();
    }
}
