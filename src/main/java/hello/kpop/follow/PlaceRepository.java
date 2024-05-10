package hello.kpop.follow;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

@Repository("follow.PlaceRepository")
public interface PlaceRepository extends Neo4jRepository<Place, Long> {
	Place findByPlaceId(Long placeId);
	Place findOneByPlaceId(Long placeId);

	void deleteByPlaceId(Long placeId);

	@Query("MATCH (p:Place {placeId:$id}) <- [:FOLLOW_PLACE] - (f:User) RETURN COUNT(f)")
	int getFollowerCount(Long id);

	@Query( "MATCH (m:Place {placeId:$placeId}) " +
			"SET m.name = $name, m.address = $address, m.idolId = $idolId " +
			"RETURN m")
	Place updateByPlaceId(long placeId, String name, String address, long idolId);
}
