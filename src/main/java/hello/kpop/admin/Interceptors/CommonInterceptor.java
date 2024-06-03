package hello.kpop.admin.Interceptors;

import hello.kpop.socialing.common.ProcessUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
public class CommonInterceptor implements HandlerInterceptor {

    //장비에 대한 초기화작업
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        init(request);
        return true;
    }

    private void init(HttpServletRequest request){
        HttpSession session = request.getSession();

        //로그인 페이지 아닐 경우 로그인 유효성 검사 세션 삭제 처리
        String URI = request.getRequestURI();
        //-1이면 로그인 페이지가 아님 (세션 삭제)
        if(URI.indexOf("/admin/user/login") == -1){
            ProcessUtils.loginInit(session);
        }


    }
}
