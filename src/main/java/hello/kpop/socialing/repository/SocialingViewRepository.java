package hello.kpop.socialing.repository;

import hello.kpop.socialing.dto.SocialingView;
import hello.kpop.socialing.dto.SocialingViewId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SocialingViewRepository extends JpaRepository<SocialingView, SocialingViewId>, QuerydslPredicateExecutor<SocialingView> {
}
