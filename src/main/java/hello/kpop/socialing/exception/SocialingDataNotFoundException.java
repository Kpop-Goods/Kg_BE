package hello.kpop.socialing.exception;

import hello.kpop.socialing.common.ProcessUtils;
import org.springframework.http.HttpStatus;


/**
 * 소셜링 데이터가 조회되지 않을때 발생하는 Exception
 * */
public class SocialingDataNotFoundException extends CommonException {

    public SocialingDataNotFoundException(String message){
        super(message, HttpStatus.BAD_REQUEST);
    }

    public SocialingDataNotFoundException() {
        super(ProcessUtils.getMessage("NotFound.SocialingData","errors"), HttpStatus.NOT_FOUND);
    }


}


