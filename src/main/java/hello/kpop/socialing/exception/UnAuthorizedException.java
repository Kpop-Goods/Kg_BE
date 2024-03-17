package hello.kpop.socialing.exception;

import hello.kpop.socialing.common.ProcessUtils;
import org.springframework.http.HttpStatus;

public class UnAuthorizedException extends CommonException{


    public UnAuthorizedException() {
        super(ProcessUtils.getMessage("UnAuthorized","errors"), HttpStatus.UNAUTHORIZED);

    }

    public UnAuthorizedException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }

}
