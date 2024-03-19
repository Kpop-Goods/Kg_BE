package hello.kpop.follow;

import hello.kpop.follow.Place;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository("Follow.PlaceRepository")
public interface PlaceRepository extends Neo4jRepository<Place, Long> {
	Place findByPlaceId(Long placeId);
	Place findOneByPlaceId(Long placeId);
	void deleteByPlaceId(Long placeId);
}
