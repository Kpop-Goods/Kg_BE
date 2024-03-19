package hello.kpop.follow;

import hello.kpop.follow.Idol;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IdolRepository extends Neo4jRepository<Idol, Long> {
	Idol findByName(String name);
	Idol findByIdolId(Long id);
	Idol findOneByIdolId(Long id);
	Optional<Idol> findById(Long id);
}
