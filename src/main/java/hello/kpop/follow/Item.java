package hello.kpop.follow;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;


@Getter
@Setter
@Node("Item")
public class Item { // item; goods
	@Id
	@GeneratedValue
	private Long id;

	private Long itemId;
	private String name;
	private Integer category;

	private Long idolId;
	private Long userId;

	public Item(Long id, String name, Integer category, Long idolId, Long userId) {
		this.itemId = id;

		this.name = name;
		this.category = category;

		this.idolId = idolId;
		this.userId = userId;
	}
}
