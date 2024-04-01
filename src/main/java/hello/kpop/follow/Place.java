package hello.kpop.follow;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;


@Getter
@Setter
@Node("Place")
public class Place {
	@Id
	private Long id;
	private Long placeId;

	private String name;

	private String address; // post number
//	private String placeName;
//	private Double latitude;
//	private Double longitude;

	private Long idolId;

	public Place(Long id, String name, String address, Long idolId) {
		this.placeId = id;

		this.name = name;
		this.address = address;

		this.idolId	= idolId;
	}
}
