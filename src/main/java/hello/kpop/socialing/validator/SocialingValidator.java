package hello.kpop.socialing.validator;

import hello.kpop.socialing.dto.SocialingRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDateTime;


//소셜 필수 항목 유효성 검증
@Component
@RequiredArgsConstructor
public class SocialingValidator implements Validator, SocialUrlValidator {


    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(SocialingRequestDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SocialingRequestDto request = (SocialingRequestDto) target;

        String name = request.getSocialing_name();
        String type = request.getType();
        String place =request.getSocial_place();
        int quota = request.getQuota();
        String url = request.getChat_url();
        LocalDateTime startDateTime = request.getStart_date();
        LocalDateTime endDateTime = request.getEnd_date();

        LocalDateTime currentDateTime = LocalDateTime.now();

            // 필수값 검증
            if (StringUtils.hasText(name)) {
                errors.rejectValue("socialing_name", "NotBlank.socialing_name");
            }

            if (StringUtils.hasText(type)) {
                errors.rejectValue("type", "NotBlank.type");
            }
            if (StringUtils.hasText(place)) {
                errors.rejectValue("social_place", "NotBlank.social_place");
            }
            if (quota <= 0 || quota > 100) {
                // 유효하지 않은 인원 수
                errors.rejectValue("quota", "NotNull.quota");
            }
            // URL 유효성 검증
            if (url != null && !url.isBlank() && !urlCheck(url)) {
                errors.rejectValue("url", "Url");
            }

            // 날짜 유효성 검증

            if (startDateTime == null) {
                errors.rejectValue("start_date", "NotNull.start_date" );
            }

            if (endDateTime == null) {
                errors.rejectValue("end_date", "NotNull.end_date");
            }

            if (startDateTime != null && endDateTime != null) {


                if (startDateTime.isAfter(endDateTime)) {
                    errors.rejectValue("start_date", "start_date.fail" );
                }

                if (startDateTime.isBefore(currentDateTime)) {
                    errors.rejectValue("start_date", "now_date.fail");
                }

                if (endDateTime.isBefore(currentDateTime)) {
                    errors.rejectValue("end_date", "end_date.fail");
                }
            }
        }
    }
