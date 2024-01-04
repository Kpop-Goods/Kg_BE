package hello.kpop.global.jwt.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import hello.kpop.global.login.service.LoginService;
import hello.kpop.user.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Key;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Getter
@Slf4j
public class JwtService {

    //@Value를 사용해 각 필드들에 설정 파일인 application-jwt.yml의 프로퍼티들을 주입하도록 했다.
    @Value("${jwt.secretKey}") //서버가 가지고 있는 개인키
    private String secretKey;

    @Value("${jwt.access.expiration}") //액세스 토큰 만료 시간
    private Long accessTokenExpirationPeriod;

    @Value("${jwt.refresh.expiration}") //리프레시 토큰 만료 시간
    private Long refreshTokenExpirationPeriod;

    @Value("${jwt.access.header}") //액세스 토큰이 담길 헤더 이름(key)
    private String accessHeader;

    @Value("${jwt.refresh.header}") //리프레시 토큰이 담길 헤더 이름(key)
    private String refreshHeader;

    /*
     * JWT의 Subject와 Claim으로 email 사용 -> 클레임의 name을 "email"으로 설정
     * JWT의 헤더에 들어오는 값 : 'Authorication(Key) = Bearer {토큰] (Value)' 형식
     * */
    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String EMAIL_CLAIM = "email";
    private static final String BEARER = "Bearer ";
    private Key key;

    private final UserRepository userRepository;
    private final LoginService loginService;

    //AccessToken 생성 메소드
    public String createAccessToken(String email) { //email은 OAuth2로 로그인한 email을 받도록 할 것임
        Date now = new Date();
        return JWT.create() //JWT 토큰을 생성하는 빌더를 생성

                //.withSubject와 .withExpirestAt()으로 JWT 토큰 Payload에 들어갈 Claim 생성
                .withSubject(ACCESS_TOKEN_SUBJECT) //JWT의 Subject 지정 -> AccessToken 이므로 AccessToken
                .withExpiresAt(new Date(now.getTime() + accessTokenExpirationPeriod)) //토큰 만료 시간 설정

                /*
                 * 클레임으로는 email 하나만 사용
                 * 추가적으로 식별자나, 이름 등의 정보를 더 추가해도 된다.
                 * 추가할 경우 .withClaim(클래임 이름, 클래임 값)으로 설정해주면 된다.
                 * */
                .withClaim(EMAIL_CLAIM, email) //사용자 정의 클레임 생성

                //사용할 알고리즘과 서버의 개인 키를 지정해주면 JWT 토큰이 암호화되어 생성된다.
                .sign(Algorithm.HMAC512(secretKey)); //HMAC512 알고리즘 사용, application-jwt.yml에서 지정한 secret 키로 암호화
    }

    /*
     * RefreshToken 생성 메소드
     * RefreshToken은 Claim에 email이 필요가 없으므로 withClaim()으로 email을 추가로지 않았다.
     * */
    public String createRefreshToken() {
        Date now = new Date();
        return JWT.create()
                .withSubject(REFRESH_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + refreshTokenExpirationPeriod))
                .sign(Algorithm.HMAC512(secretKey));
    }

    //AccessToken & RefreshToken Response Header 추가 메소드 부분
    //AccessToken 재발급 시 헤더에 실어서 보내는 메소드
    public void sendAccessToken(HttpServletResponse response, String accessToken) {
        response.setStatus(HttpServletResponse.SC_OK);

        response.setHeader(accessHeader, accessToken);
        log.info("재발급 된 Access Token : ", accessToken);
    }

    //로그인 시 AccessToken + RefreshToken 헤더에 실어서 보내는 메소드
    public void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken) {
        response.setStatus(HttpServletResponse.SC_OK);

        //엑세스 토큰, 리프레스 토큰을 헤더에 실어서 반환
        setAccessTokenHeader(response, accessToken);
        setRefreshTokenHeader(response, refreshToken);
        log.info("Access Token, Refresh Token 헤더 설정 완료");
    }

    /*
     * 헤더에 담긴 토큰 형식이 Bearer[토큰]형식이므로
     * 토큰 값을 가져오기 위해서는 Bearer를 제거해야 한다.
     * 따라서, 헤더에서 값을 가져온 후 Bearer를 제거하고 반환
     * */
    /*
     * 헤더에서 RefreshToken 추출
     * 토큰 형식 : Bearer XXX에서 Bearer를 제외하고 순수 토큰만 가져오기 위해서
     * 헤더를 가져온 후 "Bearer"를 삭제(""로 replace)
     * */
    public Optional<String> extractRefreshToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(refreshHeader))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refreshToken -> refreshToken.replace(BEARER, ""));
    }

    /*
     * 헤더에서 AccessToken 추출
     * 토큰 형식 : Bearer XXX에서 Bearer를 제외하고 순수 토큰만 가져오기 위해서
     * 헤더를 가져온 후 "Bearer"를 삭제(""로 replace)
     * */
    public Optional<String> extractAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(accessHeader))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refreshToken -> refreshToken.replace(BEARER, ""));
    }

    /*
     * AccessToken에서 유저의 Email을 추출하는 메소드 extractEmail()
     * 추출 전에 JWT.require()로 토큰 유효성을 검사하는 로직이 있는 JWT verifier builder를 반환한다.
     * 그 후 반환된 builder를 사용하여 .verify로 AccessToken 검증 후
     * 유효하다면 getClaim()으로 이메일 추출 -> .asString()으로 String으로 변환 후 유저 Email 반환
     * 유효하지 않다면 빈 Optional 객체 반환
     * */
    public Optional<String> extractEmail(String accessToken) {
        try {
            //토큰 유효성 검사하는데 사용할 알고리즘이 있는 JWT verifier builder 반환
            return Optional.ofNullable(JWT.require(Algorithm.HMAC512(secretKey))
                    .build() //반환된 빌더로 JWT verifier 생성
                    .verify(accessToken) //accessToken을 검증하고 유효하지 않다면 예외 발생
                    .getClaim(EMAIL_CLAIM) //claim(Email) 가져오기
                    .asString()); //String으로 유저 Email 반환
        } catch(Exception e) {
            log.error("액세스 토큰이 유효하지 않습니다");
            return Optional.empty();
        }
    }

    //AccessToken 헤더 설정
    public void setAccessTokenHeader(HttpServletResponse response, String accessToken) {
        response.setHeader(accessHeader, accessToken);
    }

    //RefreshToken 헤더 설정
    public void setRefreshTokenHeader(HttpServletResponse response, String refreshToken) {
        response.setHeader(refreshHeader, refreshToken);
    }

    /*
     * DB의 RefreshToken을 업데이트하는 메소드
     * 유저 회원 가입 시에 유저 Entity가 저장될 때는, RefreshToken이 발급되기 전이기 때문에
     * DB에 RefreshToken Column에 null로 저장된다.
     * 따라서, 로그인 시 RefreshToken을 발급하면서, 발급한 RefreshToken을 DB에 저장하는 메소드이다.
     * 이후 OAuth2 Login 성공 시 처리하는 LoginSuccessHandler에서 사용할 예정이다.
     */
    public void updateRefreshToken(String email, String refreshToken) {
        userRepository.findByUserEmail(email)
                .ifPresentOrElse(
                        user -> user.updateRefreshToken(refreshToken),
                        () -> new Exception("일치하는 회원이 없습니다.")
                );
    }

    /*
     * 토큰 유효성 검사 메소드
     * 클라이언트가 토큰을 헤더에 담아서 요청할 때마다 토큰 검증 단계를 거치게 된다.
     * 각 AccessToken, RefreshToken의 유효성을 검증할 때 사용되는 메소드
     * JWT.require()로 토큰 유효성을 검사하는 로직이 있는 JWT verfigier builder를 반환
     * 그 후 반환된 builder를 사용해 .verify(accesToken)으로 Token을 검증한다.
     * 토큰이 유효하지 않으면 예외를 발생시켜 false를 반환하고, 유효하면 true 반환
     */
    public boolean isTokenValid(String token) {
        try {
            JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token);
            return true;
        } catch (Exception e) {
            log.error("유효하지 않은 토큰입니다, {}", e.getMessage());
            return false;
        }
    }

//    public Long getExpiration(String accessToken) {
//        // accessToken 남은 유효시간
//        Date expiration = Jwts.parserBuilder()
//                .setSigningKey(key)
//                .build()
//                .parseClaimsJws(accessToken)
//                .getBody()
//                .getExpiration();
//        // 현재 시간
//        Long now = new Date().getTime();
//        return (expiration.getTime() - now);
//    }

//    //토큰에서 Admin 정보 추출
//    public String getAdminPk(String token) {
//        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
//    }

    //인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = loginService.loadUserByUsername(extractEmail(token).get());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }


}
