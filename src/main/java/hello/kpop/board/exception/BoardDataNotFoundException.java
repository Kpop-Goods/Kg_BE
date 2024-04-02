package hello.kpop.board.exception;

import hello.kpop.socialing.common.ProcessUtils;
import hello.kpop.socialing.common.exception.CommonException;
import org.springframework.http.HttpStatus;

public class BoardDataNotFoundException extends CommonException {

    public BoardDataNotFoundException(){
        super(ProcessUtils.getMessage("NotFound.BoardData" , "errors"), HttpStatus.NOT_FOUND);
    }
}
