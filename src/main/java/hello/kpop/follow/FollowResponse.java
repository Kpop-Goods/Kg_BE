package hello.kpop.follow;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Schema(description = "Follow Common Response Body")
public class FollowResponse {

	@Schema(example = "1, 0", description = "Success(1) or not(0)")
	public Boolean success;

	@Schema(example = "200", description = "Http Status Code in number")
	public Integer statusCode;
	@Schema(example = "Query Operation Success", description = "Text message for each response result")
	public String message;

	@Schema(example = "0, 1 or many", description = "Total number of all records in database")
	private Integer totalSize;

	@Schema(example = "0, 1 or many", description = "Number of records for response body's IDs")
	private Integer responseCount;
	@Schema(description = "array of response ID")
	private List<Long> IDs;
}
