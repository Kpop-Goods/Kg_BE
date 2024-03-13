package hello.kpop.agency.repository;

import hello.kpop.agency.Agency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AgencyRepository extends JpaRepository<Agency, Long> {

    //소속사 등록 시 중복 등록 방지
    Optional<Agency> findByAgencyName(String agencyName);

    //삭제된 데이터 분리를 위해
    List<Agency> findByDelYN(String delYN);
}
