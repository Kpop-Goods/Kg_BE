package hello.kpop.follow;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Getter
@Setter
@Node("Meet")
public class Meet {
	@Id
	private Long id;
	private Long meetId;

	private String name;

	private Long idolId;
	private Long userId;

	public Meet(Long id, String name, Long idolId, Long userId) {
		this.meetId = id;

		this.name = name;

		this.idolId = idolId;
		this.userId = userId;
	}
}
