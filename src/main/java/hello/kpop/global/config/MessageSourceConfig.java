package hello.kpop.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import hello.kpop.admin.Interceptors.CommonInterceptor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MessageSourceConfig implements WebMvcConfigurer {


    @Autowired
    private CommonInterceptor commonInterceptor;
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource ms = new ResourceBundleMessageSource();
        ms.setDefaultEncoding("UTF-8");
        ms.setBasenames("messages.commons","messages.validations", "messages.errors");
        return ms;
    }

    @Override
    //모든 인터셉터 경로
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(commonInterceptor).addPathPatterns("/**");

        //registry.addInterceptor(siteConfigInterceptor)
        //        .addPathPatterns("/**");
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
