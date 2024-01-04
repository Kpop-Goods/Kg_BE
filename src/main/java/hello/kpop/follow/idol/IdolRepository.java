package hello.kpop.follow.idol;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IdolRepository extends Neo4jRepository<Idol, Long> {

	Idol findByName(String name);

	Idol findByIdolid(Long idolid);

	Optional<Idol> findById(Long id);
}
