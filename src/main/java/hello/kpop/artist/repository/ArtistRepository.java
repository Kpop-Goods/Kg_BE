package hello.kpop.artist.repository;

import hello.kpop.artist.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
    //아티스트 등록
    Optional<Artist> findByArtistName(String artistName);

    //아티스트 전체 조회
    List<Artist> findAll();
}
