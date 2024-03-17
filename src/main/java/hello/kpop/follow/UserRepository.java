package hello.kpop.follow;

import hello.kpop.follow.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("Follow.UserRepository")
public interface UserRepository extends Neo4jRepository<User, Long> {
	User findByName(String name);
	User findOneByUserId(Long userid);
	Optional<User> findById(Long id);
}
