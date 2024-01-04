package hello.kpop.place.service;

import hello.kpop.artist.Artist;
import hello.kpop.artist.dto.SuccessResponseDto;
import hello.kpop.artist.repository.ArtistRepository;
import hello.kpop.place.Place;
import hello.kpop.place.dto.PlaceDetailDto;
import hello.kpop.place.dto.PlaceDto;
import hello.kpop.place.dto.PlaceResponseDto;
import hello.kpop.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final ArtistRepository artistRepository;

    //장소 등록
    @Transactional
//    public void savePlace(PlaceDto requestDto, Long id) {
//        Artist artist = artistRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException("아티스트 ID를 입력해주세요."));
//        Place place = new Place(artist, requestDto);
//
//        placeRepository.save(place);
//    }
    public PlaceResponseDto savePlace(PlaceDto requestDto, Long id) {
        Artist artist = artistRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아티스트 ID가 존재하지 않습니다."));
        Place place = new Place(artist, requestDto);

        placeRepository.save(place);

        return new PlaceResponseDto(place);
    }

    //장소 전체 조회
    public List<PlaceResponseDto> selectPlaceList() {
        return placeRepository.findAll().stream().map(PlaceResponseDto::new).toList();
    }

    //선택한 장소 상세 조회
    @Transactional
    public PlaceResponseDto selectPlaceOne(Long id) {
        return placeRepository.findById(id).map(PlaceResponseDto::new).orElseThrow(
                () -> new IllegalArgumentException("장소 ID가 존재하지 않습니다."));
    }

    //선택한 장소 정보 수정
    @Transactional
    public PlaceResponseDto updatePlace(Long id, PlaceDetailDto requestDto) throws Exception {
        Place place = placeRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("장소 ID가 존재하지 않습니다."));

        place.update(requestDto);
        return new PlaceResponseDto(place);
    }

    //선택한 장소 삭제
    @Transactional
    public SuccessResponseDto deletePlace(Long id) throws Exception {
        Place place = placeRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("장소 ID가 존재하지 않습니다."));

        placeRepository.deleteById(id);
        return new SuccessResponseDto(true);
    }

    //장소명(이벤트명) 검색했을 시 조회
//    @Transactional
//    public List<Place> searchName(String name) {
//        if(name.equals("")) name = "";
//        System.out.println("이름 리스트 : " + placeRepository.findByNameContaining(name));
//        return placeRepository.findByNameContaining(name);
//    }
//
    @Transactional
    public List<Place> searchPlace(String keyword) {
        System.out.println("장소리스트 : " + placeRepository.findAllSearch(keyword));
        return placeRepository.findAllSearch(keyword);
    }
}
