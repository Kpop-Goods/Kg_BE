package hello.kpop.follow;

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

	private Long idolId;
	private String name;

	private Boolean unit;
	private Boolean gender;

	public Idol(Long idolId, String name, Boolean unit, Boolean gender) {
		this.idolId = idolId;
		this.name = name;
		this.unit = unit;
		this.gender = gender;
	}

	public String toString() {
		return this.name + ", " + this.idolId;
	}
}