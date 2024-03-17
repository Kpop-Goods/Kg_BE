package hello.kpop.socialing.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;

public class BadRequestException extends CommonException {
   // 잘못된 요청이 들어왔을때 예외를 생성
    public BadRequestException(String message) {
      super(message, HttpStatus.BAD_REQUEST);
    }

    public BadRequestException(Errors errors){
        super(errors,HttpStatus.BAD_REQUEST);
    }




}
