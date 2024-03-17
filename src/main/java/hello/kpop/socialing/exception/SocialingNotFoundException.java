package hello.kpop.socialing.exception;

import hello.kpop.socialing.common.ProcessUtils;
import org.springframework.http.HttpStatus;


/**
 * 소셜링이 조회되지 않는 경우 발생하는 예외
 */
public class SocialingNotFoundException extends CommonException {
    public SocialingNotFoundException() {
        super(ProcessUtils.getMessage("NotFound.Socialing","errors"),HttpStatus.NOT_FOUND);
    }

}
