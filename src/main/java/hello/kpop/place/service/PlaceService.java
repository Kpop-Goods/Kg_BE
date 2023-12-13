package hello.kpop.place.service;

import hello.kpop.place.Place;
import hello.kpop.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;

    @Transactional
    public void signUp(Place place) throws Exception {

        Place place1 = Place.builder()
                .placeCode(place.getPlaceCode())
                .latitude(place.getLatitude())
                .longitude(place.getLongitude())
                .name(place.getName())
                .placeAddress(place.getPlaceAddress())
                .placeImg(place.getPlaceImg())
                .placeContent(place.getPlaceContent())
                .startDate(place.getStartDate())
                .endDate(place.getEndDate())
                .build();

        placeRepository.save(place1);
    }
}
