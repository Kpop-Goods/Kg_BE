package hello.kpop.goods.repository;

import hello.kpop.goods.dto.Goods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsRepository extends JpaRepository<Goods, Long>, JpaSpecificationExecutor<Goods> {

    //삭제된 데이터 분리를 위해
    List<Goods> findByDelYN(String delYN);

    //굿즈명 검색했을 시
    List<Goods> findAll(Specification<Goods> spec);

    //페이징 처리를 통한 전체 목록 조회
    Page<Goods> findAll(Pageable pageable);


}
