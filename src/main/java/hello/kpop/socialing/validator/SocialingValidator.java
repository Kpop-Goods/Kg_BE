package hello.kpop.socialing.validator;

import hello.kpop.socialing.dto.SocialingData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.LocalDateTime;


//소셜 항목 유효성 검증
@Component
@RequiredArgsConstructor
public class  SocialingValidator implements Validator, SocialUrlValidator {


    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(SocialingData.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SocialingData request = (SocialingData) target;


        String url = request.getChat_url();
        LocalDate start_date = request.getStart_date();
        LocalDate end_date = request.getEnd_date();
        LocalDateTime currentDateTime = LocalDateTime.now();

        // URL 유효성 검증
        if (url == null || url.isBlank() || !urlCheck(url)) {
            errors.rejectValue("chat_url", "Url");
        }

        // 날짜 유효성 검증

        if (start_date != null && end_date != null) {

            if (start_date.isAfter(end_date)) {
                errors.rejectValue("start_date", "start_date.fail" );
            }

            if (start_date.isBefore(currentDateTime.toLocalDate())) {
                errors.rejectValue("start_date", "now_date.fail");
            }

            if (end_date.isBefore(currentDateTime.toLocalDate())) {
                errors.rejectValue("end_date", "end_date.fail");
            }
        }
    }
}
