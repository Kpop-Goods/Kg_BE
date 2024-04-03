package hello.kpop.place.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class PageInfo {
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
}
