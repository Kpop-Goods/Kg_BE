package hello.kpop.place.repository;

import hello.kpop.artist.Artist;
import hello.kpop.place.Place;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long>, JpaSpecificationExecutor<Place> {

    //이벤트 등록 시 중복 등록 방지
    Optional<Place> findByAddress(String address);

    //삭제된 데이터 분리를 위해
    List<Place> findByDelYN(String delYN);

    //장소명(이벤트명), 장소 카테고리 코드, 아티스트코드, 소속사코드 검색했을 시
    List<Place> findAll(Specification<Place> spec);

}
