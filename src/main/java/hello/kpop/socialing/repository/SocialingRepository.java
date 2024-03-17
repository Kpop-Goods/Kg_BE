package hello.kpop.socialing.repository;


import hello.kpop.socialing.Socialing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SocialingRepository extends JpaRepository<Socialing, Long>, QuerydslPredicateExecutor<Socialing> {

//    @Query("SELECT s FROM Socialing s ORDER BY s.like_cnt ASC")
//    List<Socialing> getSocialingLike(Socialing socialing);
//
//    @Query("SELECT s FROM Socialing s ORDER BY s.view_ctn ASC ")
//    List<Socialing> getSocialingView(Socialing socialing);

    Page<Socialing> findAllByOrderByViewDesc(Pageable pageable);

    Page<Socialing> findAllByOrderByLikeDesc(Pageable pageable);

}
