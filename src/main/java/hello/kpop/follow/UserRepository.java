package hello.kpop.follow;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;
import java.util.Optional;

@Repository("follow.UserRepository")
public interface UserRepository extends Neo4jRepository<User, Long> {

	User findByName(String name);

	User findOneByUserId(Long id);

	Optional<User> findById(Long id);

	//// user
	// follow/following
	@Query("MATCH (u:User {userId:$id}) - [:FOLLOW_USER] -> (f:User) RETURN COUNT(f)")
	int getFollowUserCount(Long id);

	@Query("MATCH (u:User {userId:$userId}) - [:FOLLOW_USER] -> (f:User) RETURN f.userId")
	List<Long> getFollowUserList(Long userId);

	@Query("MATCH (u:User {userId:$userId}) - [:FOLLOW_USER] -> (f:User {userId:$followId}) RETURN COUNT(f)")
	int isFollowUser(Long userId, Long followId);

	@Query( "MATCH (u:User {userId:$userId}) - [r:FOLLOW_USER] -> (f:User {userId:$followId}) " +
			"DELETE r")
	void unfollowUser(Long userId, Long followId);

	// follower/followed
	@Query("MATCH (u:User {userId:$id}) <- [:FOLLOW_USER] - (f:User) RETURN COUNT(f)")
	int getFollowerUserCount(Long id);

	@Query("MATCH (u:User {userId:$userId}) <- [:FOLLOW_USER] - (f:User) RETURN f.userId")
	List<Long> getFollowerUserList(Long userId);

	//// idol, follow/following
	@Query("MATCH (u:User {userId:$userId}) - [:FOLLOW_IDOL] -> (f:Idol) RETURN f.idolId")
	List<Long> getFollowIdolList(Long userId);

	@Query("MATCH (u:User {userId:$userId}) - [:FOLLOW_IDOL] -> (f:Idol {idolId:$idolId}) RETURN COUNT(f)")
	int isFollowIdol(Long userId, Long idolId);

	@Query( "MATCH (u:User {userId:$userId}) - [r:FOLLOW_IDOL] -> (f:Idol {idolId:$idolId}) " +
			"DELETE r")
	void unfollowIdol(Long userId, Long idolId);

	//// item, follow/following
	@Query(	"MATCH (u:User {userId:$id}), (i:Item {itemId:$itemId}) " + //WHERE a.name = ""Shikar Dhawan"" AND b.name = ""India" +
			"CREATE (u)-[r:FOLLOW_ITEM]->(i)")
	void followItem(Long id, Long itemId);

	@Query("MATCH (u:User {userId:$userId}) - [:FOLLOW_ITEM] -> (f:Item) RETURN f.itemId")
	List<Long> getFollowItemList(Long userId);

	@Query("MATCH (u:User {userId:$userId}) - [:FOLLOW_ITEM] -> (f:Item {itemId:$itemId}) RETURN COUNT(f)")
	int isFollowItem(Long userId, Long itemId);

	@Query( "MATCH (u:User {userId:$userId}) - [r:FOLLOW_ITEM] -> (f:Item {itemId:$itemId}) " +
			"DELETE r")
	void unfollowItem(Long userId, Long itemId);

	//// meet, follow/following
	@Query(	"MATCH (u:User {userId:$userId}), (f:Meet {meetId:$meetId}) " +
			"CREATE (u)-[r:FOLLOW_MEET]->(f)")
	void followMeet(Long userId, Long meetId);

	@Query("MATCH (u:User {userId:$userId}) - [:FOLLOW_MEET] -> (f:Meet) RETURN f.meetId")
	List<Long> getFollowMeetList(Long userId);

	@Query("MATCH (u:User {userId:$userId}) - [:FOLLOW_MEET] -> (f:Meet {meetId:$meetId}) RETURN COUNT(f)")
	int isFollowMeet(Long userId, Long meetId);

	@Query( "MATCH (u:User {userId:$userId}) - [r:FOLLOW_MEET] -> (f:Meet {meetId:$meetId}) " +
			"DELETE r")
	void unfollowMeet(Long userId, Long meetId);

	//// place, follow/following
	@Query(	"MATCH (u:User {userId:$userId}), (f:Place {placeId:$placeId}) " +
			"CREATE (u)-[r:FOLLOW_PLACE]->(f)")
	void followPlace(Long userId, Long placeId);

	@Query("MATCH (u:User {userId:$userId}) - [:FOLLOW_PLACE] -> (f:Place) RETURN f.placeId")
	List<Long> getFollowPlaceList(Long userId);

	@Query("MATCH (u:User {userId:$userId}) - [:FOLLOW_PLACE] -> (f:Place {placeId:$placeId}) RETURN COUNT(f)")
	int isFollowPlace(Long userId, Long placeId);

	@Query( "MATCH (u:User {userId:$userId}) - [r:FOLLOW_PLACE] -> (f:Place {placeId:$placeId}) " +
			"DELETE r")
	void unfollowPlace(Long userId, Long placeId);
}
