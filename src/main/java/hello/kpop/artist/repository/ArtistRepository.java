package hello.kpop.artist.repository;

import hello.kpop.agency.Agency;
import hello.kpop.artist.Artist;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface ArtistRepository extends JpaRepository<Artist, Long>, JpaSpecificationExecutor<Artist> {
    //아티스트 등록 시 중복 등록 방지
    Optional<Artist> findByArtistName(String artistName);


    //아티스트 전체 조회
    List<Artist> findAll();
    //삭제된 데이터 분리를 위해
    List<Artist> findByDelYN(String delYN);

    boolean existsByAgencyAndDelYN(Agency agency, String delYN);

    //아티스트 검색했을 시
    List<Artist> findAll(Specification<Artist> spec);
}
