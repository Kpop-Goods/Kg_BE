package hello.kpop.user.handler.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomValidationException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private String message;
    private Map<String, String> errors;

}
