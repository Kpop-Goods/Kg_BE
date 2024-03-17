package hello.kpop.socialing;


import hello.kpop.socialing.common.ProcessUtils;
import hello.kpop.socialing.exception.CommonException;
import hello.kpop.socialing.common.ResponseJSONData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;

@RestControllerAdvice("hello.kpop.socialing")
@RequiredArgsConstructor
public class CommonAdvice {

    private final Utils utils;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseJSONData> errorHandler(Exception e) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // 500
        Object message = e.getMessage();

        if (e instanceof CommonException) {
            CommonException commonException = (CommonException) e;
            status = commonException.getStatus();
            Errors errors = commonException.getErrors();
            if (errors != null) {
                Map<String, List<String>> messages = utils.getErrorMessages(errors);
                if (messages != null && !messages.isEmpty()) message = messages;
            }
        }else {
            message= ProcessUtils.getMessage("Internal","errors");
        }

        e.printStackTrace();

        ResponseJSONData data = new ResponseJSONData();
        data.setSuccess(false);
        data.setStatus(status);
        data.setMessage(message);

        return ResponseEntity.status(status).body(data);
    }
}
