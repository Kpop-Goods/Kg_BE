package hello.kpop.global.login.fillter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/*
 * 스프링 시큐리티의 폼 기반의 UsernamePasswordAuthenticationFilter를 참고하여 만든 커스텀 필터
 * 거의 구조가 같고, Type이 Json인 Login만 처리하도록 설정한 부분만 다르다. (커스텀 API용 필터 구현)
 * Username : 회원 아이디 -> email로 설정
 * "/login" 요청이 왔을 때 Json 값을 매핑 처리하는 필터
 */
public class CustomJsonUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final String DEFAULT_LOGIN_REQUEST_URL = "/login"; //"/login"으로 오는 요청을 처리
    private static final String HTTP_METHOD = "POST"; //로그인 HTTP 메소드는 POST
    private static final String CONTENT_TYPE = "application/json"; //JSON 타입의 데이터로 오는 로그인 요청만 처리
    private static final String USERNAME_KEY = "userEmail"; //회원 로그인 시 이메일 요청 Json Key : "email"
    private static final String PASSWORD_KEY = "password"; //회원 로그인 시 비밀번호 요청 Json Key : "password"
    private static final AntPathRequestMatcher DEFAULT_LOGIN_PATH_REQUEST_MATCHER =
            new AntPathRequestMatcher(DEFAULT_LOGIN_REQUEST_URL, HTTP_METHOD); //"/login" + POST 로 온 요청에 매칭된다.

    private final ObjectMapper objectMapper;

    public CustomJsonUsernamePasswordAuthenticationFilter(ObjectMapper objectMapper) {
        /*
         * super()를 통해 부모 클래스인 AbstractAuthenticationProcessingFilter()의 생성자 파라미터로
         * 위에서 상수로 선언한 "/login"URL을 설정해줬다.
         * 이렇게 설정함으로써 우리가 만든 필터는 "/login"URL이 들어올 시 작동하게 된다.
         */
        super(DEFAULT_LOGIN_PATH_REQUEST_MATCHER); //위에서 설정한 "login" + POST로 온 요청을 처리하기 위해 설정
        this.objectMapper = objectMapper; //생성자의 파라미터로 ObjectMapper 받았기 때문에 objectMapper 생성자 주입 해주었다.
    }

    /*
     * 인증 처리 메소드
     *
     * 기본적으로 AbstractAuthenticationProcessingFilter의 attemptAuthentication()은 인증 처리 메소드 이다.
     * 이를 오버라이드하여 커스텀 필터에서도 인증처리를 구현하였다.
     *
     * UsernamePasswordAuthenticationFilter와 동일하게 UsernamePasswordAuthenticationToken 사용
     * StreamUtils를 통해 request에서 messageBody(JSON) 반환
     * 요청 JSON Example
     * {
     *       "email" : "aaa@bbb.com"
     *       "password" : "test123"
     * }
     * 꺼낸 messageBody를 objectMapper.readValue()로 Map으로 변환 (Key : JSON의 키 -> email, password)
     * Map의 Key(email, password)로 해당 이메일, 패스워드 추출 후
     * UsernamePasswordAuthenticationToken의 파라미터 principal, credentials에 대입
     *
     * AbstractAuthenticationProcessingFilter(부모)의 getAuthenticationManager()로 AuthenticationManager 객체를 반환 받은 후
     * authenticate()의 파라미터로 UsernamePasswordAuthenticationToken 객체를 넣고 인증 처리
     * (여기서 AuthenticationManager 객체는 ProviderManager -> SecurityConfig에서 설정)
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        /*
         * 요청의 ContentType이 null이거나 우리가 설정한 application/json이 아니라면
         * AuthenticationServiceException 예외를 발생시켜 JSON으로 요청하도록 했다.
         */
        if(request.getContentType() == null || !request.getContentType().equals(CONTENT_TYPE)) {
            throw new AuthenticationServiceException("Authentication Content-Type not supported: " + request.getContentType());
        }

        String messageBody = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);

        /*
         * JSON요청을 String으로 변환한 messageBody를 objectMapper.readValue()를 통해 Map으로 변환해
         * 각각 Email, Password를 저장했다.
         */
        Map<String, String> usernamePasswordMap = objectMapper.readValue(messageBody, Map.class);

        String userEmail = usernamePasswordMap.get(USERNAME_KEY);
        String password = usernamePasswordMap.get(PASSWORD_KEY);

        //UsernamePasswordAuthenticationToken객체는 인증 처리 객체인 AuthenticationManager가 인증 시 사용할 인증 대상 객체가 된다.
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userEmail, password); //email이 인증 대상 객체의 principal, password가 인증 대상 객체의 credentials가 된다.
        return this.getAuthenticationManager().authenticate(authRequest); //인증 처리 객체인 AuthenticationManager가 인증 성공/인증 실패 처리를 하게 된다.
    }
}
