package hello.kpop.calender.repository;

import hello.kpop.calender.Calender;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalenderRepository extends JpaRepository<Calender, Long> {
}
