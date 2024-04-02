package hello.kpop.socialing.common;


import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.http.HttpStatus;


//JSON응답 데이터
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseJSONData<T> {

    //기본값으로 성공으로 설정
    private boolean success = true;
    //기본값으로 상태코드를 200으로 설정
    private HttpStatus status = HttpStatus.OK;

    @NotNull
    //넘겨질 데이터
    private T data;
    //메세지
    private Object message;

    public ResponseJSONData( T data, Object message) {
        this.data = data;
        this.message = message;
    }

    public ResponseJSONData(T data , HttpStatus status, Object message ){
        this.data = data;
        this.status = status;
        this.message = message;
    }
}
