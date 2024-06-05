package hello.kpop.admin.commons;

import org.springframework.ui.Model;

import java.util.Map;

// 컨트롤러에서 공통적으로 작업을 처리하는 인터페이스
public interface CommonProcess {


    /**
     *  페이지 제목을 설정
     * @param model
     * @param pageTitle
     */

    // 페이지 제목을 들고 가는 메서드
    default void commonProcess(Model model , String pageTitle){
        model.addAttribute("pageTitle" , pageTitle);
    }

    // 추가 속성 설정을 위한 메서드
    default void commonProcess(Model model , String pageTitle , Map<String, Object> extras){
        model.addAttribute("pageTitle" , pageTitle);
        model.addAttribute(extras); //추가 속성 설정
    }


}
