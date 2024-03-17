package hello.kpop.follow;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

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

//	private Date startDate;
//	private Date endDate;

	public Place(Long id, String name, String address, Long idolId) {
		this.placeId = id;

		this.name = name;
		this.address = address;

		this.idolId	= idolId;

//		this.startDate = startDate;
//		this.endDate = endDate;
	}
}
