package hello.kpop.socialing.common;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Utils {

    private final MessageSource messageSource;


    public Map<String, List<String>> getErrorMessages(Errors errors) {

        Map<String, List<String>> messages = errors.getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, e -> _getErrorMessages(e.getCodes())));

        return messages;
    }

    private List<String> _getErrorMessages(String[] codes) {
        List<String> messages = Arrays.stream(codes)
                .map(c -> {
                    try {
                        String message = messageSource.getMessage(c, null, null);
                        return message;
                    } catch (Exception e) {
                        return "";
                    }
                })
                .filter(s -> !s.isBlank()).toList();

        return messages;
    }
}
