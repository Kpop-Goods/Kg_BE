package hello.kpop.follow.place;

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
	private Long placeid;

	private String name;

	private String address;
//	private Double latitude;
//	private Double longitude;

//	private String placeName;
	private Date startDate;
	private Date endDate;

	public Place(Long id, String name, String address, Date startDate, Date endDate) {
		this.placeid = id;

		this.name = name;
		this.address = address;

		this.startDate = startDate;
		this.endDate = endDate;
	}
}
