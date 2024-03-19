package hello.kpop.follow;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetRepository extends Neo4jRepository<Meet, Long> {
	Meet findByMeetId(Long meetId);
	void deleteByMeetId(Long meetId);
}
