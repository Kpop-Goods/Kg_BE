package hello.kpop.artist.repository;

import hello.kpop.agency.Agency;
import hello.kpop.artist.Artist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface ArtistRepository extends JpaRepository<Artist, Long>, JpaSpecificationExecutor<Artist> {
    //아티스트 등록 시 중복 등록 방지
    Optional<Artist> findByArtistName(String artistName);

    //삭제된 데이터 분리를 위해
    List<Artist> findByDelYN(String delYN);

    //아티스트 검색했을 시
    List<Artist> findAll(Specification<Artist> spec);

    //페이징 처리를 통한 전체 목록 조회
    Page<Artist> findAll(Pageable pageable);
}
