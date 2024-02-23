package hello.kpop.calendar;


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
@Schema(description = "Calendar Common ResponseBody") // not using "name =" field
public class CalendarResponse {

    @Schema(example = "1, 0", description = "Success(1) or not(0)")
    public Boolean success;
    //	@JsonValue
    @Schema(example = "200", description = "Http Status Code in number")
    public Integer statusCode;
    @Schema(example = "Query Operation Success", description = "Text message for each response result")
    public String message;

    @Schema(example = "0, 1 or many", description = "Number of records for response body's Calendar DTO")
    private Integer planCount;
    //	@Schema(name = "Array of Calendar DTOs", example = "{100, 1, 'BTS', '2021/12/24 23:59:59', '2021/12/25 00:00:01', 'http:christmas', 'nothing'", description = "array of Calendar DTO")
    @Schema(description = "array of Calendar DTO")
    private List<CalendarDto> plans;
//		Long id;		// id (pk)
//		Long artistId;	// artist id (fk)
//		String name;	// name
//		Date start;		// start date, time
// 		Date end;		// end date, time
//		String link;	// external link
//		String meta;	// meta data
}

