package hello.kpop.follow;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.*;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@Node("User")
public class User {
	@Id
	@GeneratedValue
	private Long id;

	private Long userId;
	private String name;
	private String email;

	public User(Long userId, String name, String email) {
		this.userId = userId;
		this.name = name;
		this.email = email;
	}

	@Relationship(type = "FOLLOW_USER")
	public Set<User> users;

	public void followWith(User user) {
		if (users == null) {
			users = new HashSet<>();
		}
		users.add(user);
	}

	// get count of following users
	public int followUserCount() {
		if (users == null) return 0;

		return users.size();
	}

	@Relationship(type = "FOLLOW_IDOL")
	public Set<Idol> idols;

	public void followWith(Idol idol) {
		if (idols == null) {
			idols = new HashSet<>();
		}
		idols.add(idol);
	}

	// get count of following idols
	public int followIdolCount() {
		if (idols == null) return 0;

		return idols.size();
	}

	public String toString() {
		return this.name + ", " + this.userId + " follows => "
				+ Optional.ofNullable(this.users)
				.orElse(Collections.emptySet())
				.stream()
				.map(User::getName)
				.collect(Collectors.toList());
	}
}
