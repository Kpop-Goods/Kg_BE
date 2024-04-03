package hello.kpop.socialing.repository;

import hello.kpop.socialing.entity.SocialingView;
import hello.kpop.socialing.entity.SocialingViewId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SocialingViewRepository extends JpaRepository<SocialingView, SocialingViewId>, QuerydslPredicateExecutor<SocialingView> {
}
