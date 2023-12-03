package hello.kpop.board.reposiotry;

import hello.kpop.board.entity.NoticeBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<NoticeBoard, Long> {


}
