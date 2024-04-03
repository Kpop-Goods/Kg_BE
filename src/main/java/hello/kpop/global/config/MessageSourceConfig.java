package hello.kpop.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class MessageSourceConfig {
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource ms = new ResourceBundleMessageSource();
        ms.setDefaultEncoding("UTF-8");
        ms.setBasenames("messages.validations", "messages.errors");
        return ms;
    }
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

        @Bean
        public ObjectMapper objectMapper() {
            ObjectMapper om = new ObjectMapper();
            om.registerModule(new JavaTimeModule());
            om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false); // 날짜를 문자열로 직렬화

            return om;
        }
}
