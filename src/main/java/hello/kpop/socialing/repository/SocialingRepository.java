package hello.kpop.socialing.repository;


import hello.kpop.socialing.entity.Socialing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface SocialingRepository extends JpaRepository<Socialing, Long>, QuerydslPredicateExecutor<Socialing> {

//    @Query("SELECT s FROM Socialing s ORDER BY s.like_cnt ASC")
//    List<Socialing> getSocialingLike(Socialing socialing);
//
//    @Query("SELECT s FROM Socialing s ORDER BY s.view_ctn ASC ")
//    List<Socialing> getSocialingView(Socialing socialing);

    List<Socialing> findAllByOrderByViewDesc();

    List<Socialing> findAllByOrderByLikeDesc();


}
