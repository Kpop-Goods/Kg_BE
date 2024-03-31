package hello.kpop.follow;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface IdolRepository extends Neo4jRepository<Idol, Long> {

	Idol findByName(String name);

	Idol findByIdolId(Long id);
	Idol findOneByIdolId(Long id);

	Optional<Idol> findById(Long id);

	// follower/followed
	@Query("MATCH (u:Idol {idolId:$idolId}) <- [:FOLLOW_IDOL] - (f:User) RETURN COUNT(f)")
	int getFollowerCount(Long $idolId);

	@Query("MATCH (u:Idol {idolId:$idolId}) <- [:FOLLOW_IDOL] - (f:User) RETURN f.userId")
	List<Long> getFollowerList(Long idolId);
}
