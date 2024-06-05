package hello.kpop.admin.exception;

import hello.kpop.socialing.common.exception.CommonException;
import org.springframework.http.HttpStatus;

public class AlertException extends CommonException {
    public AlertException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
