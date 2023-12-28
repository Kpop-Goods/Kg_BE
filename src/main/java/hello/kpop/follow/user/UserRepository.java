package hello.kpop.follow.user;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("Follow.UserRepository")
public interface UserRepository extends Neo4jRepository<User, Long> {

	User findByName(String name);

	User findOneByUserid(Long userid);

	Optional<User> findById(Long id);
}
