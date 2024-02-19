package hello.kpop.board.repository;

import hello.kpop.board.dto.NoticeBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<NoticeBoard, Long> {





}
