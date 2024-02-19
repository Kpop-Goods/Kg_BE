package hello.kpop.socialing.common;

import org.springframework.http.HttpStatus;

//공통 익셉션
public class CommonException extends RuntimeException {
    private HttpStatus status;

    public CommonException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public CommonException(HttpStatus status) {
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }


}

