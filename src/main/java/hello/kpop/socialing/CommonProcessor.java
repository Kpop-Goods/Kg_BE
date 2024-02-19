package hello.kpop.socialing;


import hello.kpop.socialing.common.CommonException;
import hello.kpop.socialing.common.ResponseJSONData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("hello.kpop.socialing")
public class CommonProcessor {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseJSONData<Object>> errorHandler(Exception e) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // 500
        //들어오는 익셉션 인스턴스를 확인
        if (e instanceof CommonException) {
            CommonException commonException = (CommonException)e;
            status = commonException.getStatus();
        }
        //기본값으로 생성해둔 JSONData가 변경이 됨
        ResponseJSONData<Object> data = new ResponseJSONData<>();
        data.setSuccess(false);
        data.setStatus(status);
        data.setMessage(e.getMessage());

        e.printStackTrace();

        return ResponseEntity.status(status).body(data);
    }
}
