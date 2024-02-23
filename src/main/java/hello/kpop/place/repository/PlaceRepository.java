package hello.kpop.place.repository;

import hello.kpop.place.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Long> {

    //장소 전체 조회
    List<Place> findAll();

    //장소명(이벤트명) 검색했을 때만 조회(장소 필터링)
    //List<Place> findByNameContaining(String name);

    //장소명(이벤트명) 검색했을 때만 조회(장소 필터링) 쿼리방법
    @Query(value = "SELECT * FROM PLACES p WHERE p.placeName LIKE %:keyword% OR p.placeContent LIKE %:keyword%", nativeQuery = true)
    List<Place> findAllSearch(String keyword);

}
