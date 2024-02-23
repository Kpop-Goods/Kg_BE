package hello.kpop.socialing.common;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ResourceBundle;

/**
 * 공통 예외
 *
 * 상태 코드와 예외 메세지 함께 처리 가능한 예외
 * 모든 예외의 기준이 되는 예외이며 모든 예외가 상속 받아야 합니다.
 */
@Component
@RequiredArgsConstructor
public class ProcessUtils {

    private static final ResourceBundle validationsBundle;
    private static final ResourceBundle errorsBundle;

    static {
        //메세지 코드를 불러옴 (resources)
        validationsBundle = ResourceBundle.getBundle("messages.validations");
        errorsBundle = ResourceBundle.getBundle("messages.errors");
    }
    //bundle 타입에 따라서 나오는 값이 바뀌게
    public static String getMessage(String code, String bundleType) {
        try {
            bundleType = StringUtils.hasText(bundleType) ? bundleType : "validations";

            ResourceBundle bundle = null;

            if (bundleType.equals("errors")) {
                bundle = errorsBundle;
            } else {
                bundle = validationsBundle;
            }

            return bundle.getString(code);
        } catch(Exception e){
            e.printStackTrace();
            return "";
        }
    }

    public static int onlyPositiveNumber(int num, int replace) {
        return num < 1 ? replace : num;
    }

    public static String getMessage(String code) {
        return getMessage(code, null);
    }

}



