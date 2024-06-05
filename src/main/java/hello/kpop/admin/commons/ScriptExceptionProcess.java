package hello.kpop.admin.commons;

import hello.kpop.admin.exception.AlertBackException;
import hello.kpop.admin.exception.AlertException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;


/**
 *  자바 스크립트로 예외를 처리하는 인터페이스
 */
public interface ScriptExceptionProcess {


    @ExceptionHandler(AlertException.class)
    default String scriptHandler(AlertException e , Model model , HttpServletResponse response){
        response.setStatus(e.getStatus().value());
        String script = String.format("alert('%s');", e.getMessage());
        if (e instanceof AlertBackException) {
            script += "history.back();";
        }

        model.addAttribute("script", script);
        e.printStackTrace();

        return "common/_execute_script";
    }
}
