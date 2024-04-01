package hello.kpop.place.common;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class MultiResponseDto<T> {
    private List<T> data;

    public MultiResponseDto(List<T> data) {
        this.data = data;
    }
}
