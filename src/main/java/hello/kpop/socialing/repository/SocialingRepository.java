package hello.kpop.socialing.repository;


import hello.kpop.socialing.Socialing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SocialingRepository extends JpaRepository<Socialing, Long>, QuerydslPredicateExecutor<Socialing> {



}
