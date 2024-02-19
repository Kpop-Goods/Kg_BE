package hello.kpop.socialing.validator;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface SocialUrlValidator {


    //url패턴 정규패턴 검증
    default boolean urlCheck(String url) {
        Pattern p = Pattern.compile("^(?:https?:\\/\\/)?(?:www\\.)?[a-zA-Z0-9./]+$");
        Matcher m = p.matcher(url);
        return m.matches();
    }
}
