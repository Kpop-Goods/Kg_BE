package hello.kpop.socialing.repository;

import hello.kpop.socialing.Socialing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialingRepository extends JpaRepository<Socialing, Long> {
}
