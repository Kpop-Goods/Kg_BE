package hello.kpop.socialing.exception;

import hello.kpop.socialing.common.CommonException;
import org.springframework.http.HttpStatus;

public class BadRequestException extends CommonException {
   // 잘못된 요청이 들어왔을때 예외를 생성
    public BadRequestException(String message) {
      super(message, HttpStatus.BAD_REQUEST);
    }




}
