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
        LocalDate startDateTime = request.getStart_date();
        LocalDate endDateTime = request.getEnd_date();
        LocalDateTime currentDateTime = LocalDateTime.now();


            // URL 유효성 검증
            if (url == null || url.isBlank() || !urlCheck(url)) {
                errors.rejectValue("chat_url", "Url");
            }

            // 날짜 유효성 검증

            if (startDateTime != null && endDateTime != null) {

                if (startDateTime.isAfter(endDateTime)) {
                    errors.rejectValue("start_date", "start_date.fail" );
                }

                if (startDateTime.isBefore(currentDateTime.toLocalDate())) {
                    errors.rejectValue("startDate", "now_date.fail");
                }

                if (endDateTime.isBefore(currentDateTime.toLocalDate())) {
                    errors.rejectValue("endDate", "end_date.fail");
                }


            }
        }
    }
