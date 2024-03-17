package hello.kpop.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.kpop.global.jwt.fillter.JwtAuthenticationProcessingFilter;
import hello.kpop.global.jwt.service.JwtService;
import hello.kpop.global.login.fillter.CustomJsonUsernamePasswordAuthenticationFilter;
import hello.kpop.global.login.handler.LoginFailureHandler;
import hello.kpop.global.login.handler.LoginSuccessHandler;
import hello.kpop.global.login.service.LoginService;
import hello.kpop.global.oauth2.handler.OAuth2LoginFailureHandler;
import hello.kpop.global.oauth2.handler.OAuth2LoginSuccessHandler;
import hello.kpop.global.oauth2.service.CustomOAuth2UserService;
import hello.kpop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

/**
 * 인증은 CustomJsonUsernamePasswordAuthenticationFilter에서 authenticate()로 인증된 사용자로 처리
 * JwtAuthenticationProcessingFilter는 AccessToken, RefreshToken 재발급
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final LoginService loginService;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final RedisTemplate<String, String> redisTemplate; // RedisTemplate 주입

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(f->f.disable()) // FormLogin 사용 X
                .httpBasic(h -> h.disable()) //HTTP 기본 인증을 비활성화
                .csrf(AbstractHttpConfigurer::disable) //CSRF 보호 기능 비활성화
                .headers((headers) -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))

                // 세션 사용하지 않으므로 STATELESS로 설정
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ) //세션관리 정책을 STATELESS(세션이 있으면 쓰지도 않고, 없으면 만들지도 않는다)


                //== URL별 권한 관리 옵션 ==//
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user/userSignUp").permitAll()
                        // 아이콘, css, js 관련
                        // 기본 페이지, css, image, js 하위 폴더에 있는 자료들은 모두 접근 가능
                        .requestMatchers("/","/css/**","/images/**","/js/**","/favicon.ico").permitAll()
                        .requestMatchers("/user/**").permitAll()
                        .requestMatchers("/email/**").authenticated()
                        .requestMatchers("/social/**").permitAll()
                        .anyRequest().authenticated()) // 위의 경로 이외에는 모두 인증된 사용자만 접근 가능

                //== 소셜 로그인 설정 ==//
                .oauth2Login((oauth2) -> oauth2 // OAuth2 로그인 설정시작
                        .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
                                .userService(customOAuth2UserService)) //OAuth 로그인 시 사용자 정보를 가져오는 엔드포인트와 사용자 서비스를 설정
                        .failureHandler(oAuth2LoginFailureHandler)
                        .successHandler(oAuth2LoginSuccessHandler));


        // 원래 스프링 시큐리티 필터 순서가 LogoutFilter 이후에 로그인 필터 동작
        // 따라서, LogoutFilter 이후에 우리가 만든 필터 동작하도록 설정
        // 순서 : LogoutFilter -> JwtAuthenticationProcessingFilter -> CustomJsonUsernamePasswordAuthenticationFilter
        http.addFilterAfter(customJsonUsernamePasswordAuthenticationFilter(), LogoutFilter.class);
        http.addFilterBefore(jwtAuthenticationProcessingFilter(), CustomJsonUsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * AuthenticationManager 설정 후 등록
     * PasswordEncoder를 사용하는 AuthenticationProvider 지정 (PasswordEncoder는 위에서 등록한 PasswordEncoder 사용)
     * FormLogin(기존 스프링 시큐리티 로그인)과 동일하게 DaoAuthenticationProvider 사용
     * UserDetailsService는 커스텀 LoginService로 등록
     * 또한, FormLogin과 동일하게 AuthenticationManager로는 구현체인 ProviderManager 사용(return ProviderManager)
     *
     */
    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(loginService);
        return new ProviderManager(provider);
    }

    /**
     * 로그인 성공 시 호출되는 LoginSuccessJWTProviderHandler 빈 등록
     */
    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler(jwtService, userRepository, redisTemplate); // RedisTemplate 전달
    }

    /**
     * 로그인 실패 시 호출되는 LoginFailureHandler 빈 등록
     */
    @Bean
    public LoginFailureHandler loginFailureHandler() {
        return new LoginFailureHandler();
    }

    /**
     * CustomJsonUsernamePasswordAuthenticationFilter 빈 등록
     * 커스텀 필터를 사용하기 위해 만든 커스텀 필터를 Bean으로 등록
     * setAuthenticationManager(authenticationManager())로 위에서 등록한 AuthenticationManager(ProviderManager) 설정
     * 로그인 성공 시 호출할 handler, 실패 시 호출할 handler로 위에서 등록한 handler 설정
     */
    @Bean
    public CustomJsonUsernamePasswordAuthenticationFilter customJsonUsernamePasswordAuthenticationFilter() {
        CustomJsonUsernamePasswordAuthenticationFilter customJsonUsernamePasswordLoginFilter
                = new CustomJsonUsernamePasswordAuthenticationFilter(objectMapper);
        customJsonUsernamePasswordLoginFilter.setAuthenticationManager(authenticationManager());
        customJsonUsernamePasswordLoginFilter.setAuthenticationSuccessHandler(loginSuccessHandler());
        customJsonUsernamePasswordLoginFilter.setAuthenticationFailureHandler(loginFailureHandler());
        return customJsonUsernamePasswordLoginFilter;
    }

    @Bean
    public JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter() {
        return new JwtAuthenticationProcessingFilter(jwtService, userRepository, redisTemplate); // RedisTemplate 전달
    }
}
