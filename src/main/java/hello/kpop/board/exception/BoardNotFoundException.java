package hello.kpop.board.exception;

import hello.kpop.socialing.common.ProcessUtils;
import hello.kpop.socialing.common.exception.CommonException;
import org.springframework.http.HttpStatus;

public class BoardNotFoundException extends CommonException {

    public BoardNotFoundException() {
        super(ProcessUtils.getMessage("NotFound.Board","errors"),HttpStatus.BAD_REQUEST);
    }
}
