package hello.kpop.follow;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;


@Getter
@Setter
@Node("Idol")
public class Idol {
	@Id
	@GeneratedValue
	private Long id;

//	@Property
	private Long idolId;
	private String name;

	private Boolean unit;
	private Boolean gender;
//	private int agency;

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
