package hello.kpop.follow.place;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository("Follow.PlaceRepository")
public interface PlaceRepository extends Neo4jRepository<Place, Long> {

	Place findByPlaceid(Long placeId);

	void deleteByPlaceid(Long placeId);
}
