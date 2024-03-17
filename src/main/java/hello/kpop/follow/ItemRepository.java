package hello.kpop.follow;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends Neo4jRepository<Item, Long> {
	Item findByItemId(Long itemId);
	void deleteByItemId(Long itemId);
}
