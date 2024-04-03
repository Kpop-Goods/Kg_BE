package hello.kpop.board.exception;

import hello.kpop.socialing.common.ProcessUtils;
import hello.kpop.socialing.common.exception.CommonException;
import org.springframework.http.HttpStatus;

public class ForbiddenBoard extends CommonException {

    public ForbiddenBoard(){
        super(ProcessUtils.getMessage("NoBoard","errors"), HttpStatus.FORBIDDEN);
    }
}
