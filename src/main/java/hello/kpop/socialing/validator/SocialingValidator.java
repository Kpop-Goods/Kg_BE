package hello.kpop.socialing.validator;

import hello.kpop.socialing.dto.SocialingRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


//소셜 유효성 검증
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
        String url = request.getChat_url();

        // 제목 필드 검증
        if (!StringUtils.hasText(request.getSocialing_name())) {
            errors.rejectValue("socialing_name", "NotBlank.socialing_name", "제목을 입력해 주세요");
        }

        if(url != null && !url.isBlank() && !urlCheck(url)){
             errors.rejectValue("url" , "Url");
        }


    }
}
