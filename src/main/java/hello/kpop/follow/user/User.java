package hello.kpop.follow.user;

import hello.kpop.follow.place.Place;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Node
public class User {
	@Id
	@GeneratedValue
	private Long id;

	private Long userid;
	private String name;
	private String email;

	public User(Long userid, String email, String name) {
		this.userid = userid;
		this.email = email;
		this.name = name;
	}

	// relationships
	@Relationship(type = "FOLLOW_USER")
	public Set<User> users;

	public void followWith(User user) {
		if (user == null) {
			users = new HashSet<>();
		}
		users.add(user);
	}

	@Relationship(type = "FOLLOW_PLACE", direction = Relationship.Direction.OUTGOING)
	public Set<Place> places;

	public void followWith(Place place) {
		if (place == null) places = new HashSet<>();

		places.add(place);
	}
}
