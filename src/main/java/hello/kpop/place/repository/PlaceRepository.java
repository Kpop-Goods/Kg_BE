package hello.kpop.place.repository;

import hello.kpop.artist.Artist;
import hello.kpop.place.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long>, JpaSpecificationExecutor<Place> {

    //이벤트 등록 시 중복 등록 방지1
    Optional<Place> findByAddress(String address);

    //삭제된 데이터 분리를 위해
    List<Place> findByDelYN(String delYN);

    //장소명(이벤트명), 장소 카테고리 코드, 아티스트코드, 소속사코드 검색했을 시
    List<Place> findAll(Specification<Place> spec);

    //페이징 처리를 통한 전체 목록 조회
    Page<Place> findAll(Pageable pageable);

    //이벤트 등록 시 중복 등록 방지2
//    Optional<Place> findByArtistId(Artist artist);

}
