package hello.kpop.follow;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface MeetRepository extends Neo4jRepository<Meet, Long> {
	Meet findByMeetId(Long meetId);
	void deleteByMeetId(Long meetId);

	@Query("MATCH (i:Meet {meetId:$id}) <- [:FOLLOW_MEET] - (f:User) RETURN COUNT(f)")
	int getFollowerCount(Long id);

	@Query( "MATCH (m:Meet {meetId:$meetId}) " +
			"SET m.name = $name, m.idolId = $idolId, m.userId = $userId " +
			"RETURN m")
	Meet updateByMeetId(long meetId, String name, long idolId, long userId);
}
