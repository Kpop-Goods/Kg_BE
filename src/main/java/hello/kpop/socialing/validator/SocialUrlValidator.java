package hello.kpop.socialing.validator;


import java.util.regex.Pattern;

public interface SocialUrlValidator {


    //url패턴 정규패턴 검증
    default boolean urlCheck(String url) {
      //  Pattern pattern = Pattern.compile("^(?:https?:\\/\\/)?(?:www\\.)?[a-zA-Z0-9./]+$");
        Pattern pattern = Pattern.compile("^(https?|ftp)://[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)+([/?].*)?$");
        return pattern.matcher(url).matches();

    }
}
