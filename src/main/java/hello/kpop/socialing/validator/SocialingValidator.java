package hello.kpop.socialing.validator;

import hello.kpop.socialing.dto.SocialingRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


//소셜 URL패턴검증
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

        if(url != null && !url.isBlank() && !urlCheck(url)){
             errors.rejectValue("url" , "Url");
        }


    }
}
