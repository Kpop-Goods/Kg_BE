package hello.kpop.socialing.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//목록과 페이지 데이터를 담는 클래스
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListData<T> {
    private List<T> content;
    private Pagination pagination;
}