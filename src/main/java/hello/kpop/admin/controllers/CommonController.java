package hello.kpop.admin.controllers;

import hello.kpop.socialing.common.exception.CommonException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;



@Slf4j
@ControllerAdvice("hello.kpop.admin.controllers")
public class CommonController {

    @ExceptionHandler(Exception.class)
    public String errorHandler(Exception e, Model model, HttpServletRequest request, HttpServletResponse response) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        //만든 예외면 상태코드 변경 아니면 500
        if (e instanceof CommonException) {
            CommonException commonException = (CommonException)e;
            status = commonException.getStatus();
        }

        response.setStatus(status.value());

        Map<String, String> attrs = new HashMap<>();
        attrs.put("status", String.valueOf(status.value()));
        attrs.put("path", request.getRequestURI());
        attrs.put("method", request.getMethod());
        attrs.put("message", e.getMessage());
        attrs.put("timestamp", LocalDateTime.now().toString());

        model.addAllAttributes(attrs);

        Writer writer = new StringWriter();
        PrintWriter pr = new PrintWriter(writer);

        e.printStackTrace(pr);

        String errorMessage = ((StringWriter)writer).toString();
        log.error(errorMessage);

        return "error/common";
    }
}

