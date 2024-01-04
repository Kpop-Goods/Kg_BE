package hello.kpop.follow.idol;

import lombok.Getter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Getter
@Node("Idol")
public class Idol {
	@Id
	@GeneratedValue
	private Long id;

// @Property
	private Long idolid;
	private String name;

	public Idol(Long idolId, String name) {
		this.idolid = idolId;
		this.name = name;
	}

	public String toString() {
		return this.name + ", " + this.idolid;
	}
}
