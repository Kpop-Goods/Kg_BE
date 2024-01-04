package hello.kpop.global.jwt.fillter;

import hello.kpop.global.jwt.service.JwtService;
import hello.kpop.global.jwt.util.PasswordUtil;
import hello.kpop.user.User;
import hello.kpop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationProcessingFilter extends OncePerRequestFilter {

    /*
     * 클라이언트가 헤더에 JWT 토큰을 담아서 "/login" URL 이외의 요청을 보냈을 시,
     * 해당 토큰들의 유효성을 검사하여 인증 처리/ 인증 실패/ 토큰 재발급 등을 수행하는 역할의 필터이다.
     */

    /*
     * JWT 인증 필터
     * "/login" 이외의 URI 요청이 들어왔을 때 처리하는 필터
     *
     * 기본적으로 사용자는 요청 헤더에 AccessToken만 담아서 요청
     * AccessToken 만료 시에만 RefreshToken을 요청 헤더에 AccessToken과 함께 요청
     *
     * 1. RefreshToken이 없고, AccessToken이 유효한 경우 -> 인증 성공 처리, RefreshToken을 재발급하지는 않는다.
     * 2. RefreshToken이 없고, AccessToken이 없거나 유효하지 않은 경우 -> 인증 실패 처리, 403 ERROR
     * 3. RefreshToken이 있는 경우 -> DB의 RefreshToken과 비교하여 일치하면 AccessToken 재발급, RefreshToken 재발급(RTR 방식)
     *                             인증 성공 처리는 하지 않고 실패 처리
     * */

    //"/login"으로 들어오는 요청은 Fillter 작동 x
    private static final String NO_CHECK_URL = "/login";

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RedisTemplate<String, String> redisTemplate;

    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    //해당 메소드 안에 인증 처리, 인증 실패, 토큰 재발급 로직을 설정하여
    //필터 진입 시 인증 처리, 인증 실패, 토큰 재발급 등을 처리한다.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if(request.getRequestURI().equals(NO_CHECK_URL)) {
            /*
             * "/login"요청이 들어오면 fillterChain.doFilter()를 호출하여
             * 현재 필터를 통과하고, 순서에 맞는 다음 필터를 호출하여 넘어간다.
             * 이때, return;을 통해 다음 필터를 호출한 다음 현재 필터의 진행을 막는다.
             */
            filterChain.doFilter(request, response); //"/login" 요청이 들어오면, 다음 필터 호출
            return; //return으로 이후 현재 필터 진행 막기(안 해주면 아래로 내려가서 계속 필터 진행시킴)
        }

        /*
         * 사용자 요청 헤더에서 RefreshToken 추출
         * filter()를 통해 유효한 RefreshToken을 반환한다.
         * -> RefreshToken이 없거나 유효하지 않다면(DB에 저장된 RefreshToken과 다르다면) null 반환
         * 클라이언트의 요청 헤더에 RefreshToken이 있는 경우는, AccessToken이 만료되어 요청한 경우밖에 없다.
         * 따라서, RefreshToken이 있는 경우는 RefreshToken 비교 후 AccessToken을 재발급하면 된다.
         */
        String refreshToken = jwtService.extractRefreshToken(request)
                .filter(jwtService::isTokenValid)
                .orElse(null);

        /*
         * RefreshToken이 존재하고, 유효할 때 처리하는 로직
         * 리프레시 토큰이 요청헤더에 존재했다면, 클라이언트가 AccessToken이 만료되어서 요청한 것이므로
         * RefreshToken까지 보낸 것이므로 리프레시 토큰이 DB의 리프레시 토큰과 일치하는지 판단 후,
         * 일치한다면 AccessToken/RefreshToken을 재발급 해준다.
         */
        if (refreshToken != null) {
            checkRefreshTokenAndReIssueAccessToken(response, refreshToken);
            return; //RefreshToken을 보낸 경우에는 AccessToken을 재발급하고 인증 처리는 하지 않게 하기 위해 바로 return으로 필터 진행 막기
        }

        /*
         * RefreshToken이 없거나 유효하지 않다면, AccessToken을 검사하고 인증을 처리하는 로직 수행
         * AccessToken이 없거나 유효하지 않다면, 인증 객체가 담기지 않은 상태로 다음 필터로 넘어가기 때문에 403에러 발생
         * AccessToken이 유효하다면, 인증 객체가 담긴 상태로 다음 필터로 넘어가기 때문에 인증 성공
         */
        if (refreshToken == null) {//리프레시 토큰이 존재하지 않거나 유효하지 않을 때 처리하는 로직
            //checkAccessTokenAndAuthentication() : 액세스 토큰의 유효성을 검증하고 인증 성공, 실패 처리를 한다.
            checkAccessTokenAndAuthentication(request, response, filterChain);
        }

        // 1. Request Header 에서 JWT 토큰 추출
        String token = jwtService.extractAccessToken((HttpServletRequest) request).orElse(null);
        System.out.println("토큰: " + token);

        // 2. validateToken 으로 토큰 유효성 검사
        if (token != null && jwtService.isTokenValid(token)) {
            // 이메일을 이용해서 로그아웃 여부 확인

            String email = jwtService.extractEmail(token).get();
            String isLogout = (String) redisTemplate.opsForValue().get(email);

            if (ObjectUtils.isEmpty(isLogout)) {
                // 로그아웃 상태가 아니라면 토큰에서 Authentication 객체를 가져와서 SecurityContext에 저장
                Authentication authentication = jwtService.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }



    }

    /* [리프레시 토큰으로 유저 정보 찾기 & 엑세스 토큰/리프레시 토큰 재발급 메소드]
     * 파라미터로 들어온 헤더에서 추출한 리프레시 토큰으로 DB에서 유저 찾고,
     * 해당 유저가 있다면 JwtService.createAccessToken()으로 AccessToken 생성,
     * reIssueRefreshToken()으로 리프레시 토큰 재발급 & DB에 리프레시 토큰 업데이트 메소드 호출
     * 그 후 JwtServcie.sendAccessTokenAndRefreshToken()으로 응답 헤더에 보내기
     */
    private void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response, String refreshToken) {
        userRepository.findByRefreshToken(refreshToken) //추출한 RefreshToken을 통해 여기서 유저를 찾는다.
                .ifPresent(user -> { //유저가 존재한다면
                    //리프레시 토큰을 재발급하는 reIssueRefreshToken() 호출하여 리프레시 토큰 재발급
                    String reIssuedRefreshToken = reIssueRefreshToken(user);
                    jwtService.sendAccessAndRefreshToken(response, jwtService.createAccessToken(user.getUserEmail()), //jwtService.createAccessToken(user.getEmail())로 액세스 토큰 재발급
                            reIssuedRefreshToken);
                }); //재발급한 액세스 토큰, 리프레시 토큰을 Response에 보낸다.
    }

    /*
     * [리프레시 토큰 재발급 & DB에 리프레시 토큰 업데이트 메소드]
     * jwtService.createRefreshToken()으로 리프레시 토큰 재발급 후
     * DB에 재발급한 리프레시 토큰 업데이트 후 Flush
     */
    private String reIssueRefreshToken(User user) {
        String reIssuedRefreshToken = jwtService.createRefreshToken(); //리프레시 토큰 생성
        user.updateRefreshToken(reIssuedRefreshToken); //DB의 리프레시 토큰 업데이트 해준다.
        userRepository.saveAndFlush(user);
        return reIssuedRefreshToken; //재발급한 리프레시 토큰 반환
    }

    /*
     * [엑세스 토큰 체크 & 인증 처리 메소드]
     * request에서 extractAccessToken()으로 엑세스 토큰 추출 후, isTokenValid()로 유효한 토큰인지 검증
     * 유효한 토큰이면, 액세스 토큰에서 extractEmail로 Email을 추출한 후 findByEmail()로 해당 이메일을 사용하는 유저 객체 반환
     * 그 유저 객체를 saveAuthentication()으로 인증 처리하여
     * 인증 허가 처리된 객체를 SecurityContextHolder에 담기
     * 그 후 다음 인증 필터로 진행
     */
    public void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("checkAccessTokenAndAuthentication() 호출");
        jwtService.extractAccessToken(request) //액세스 토큰 추출
                .filter(jwtService::isTokenValid) //유효성 검증
                .ifPresent(accessToken ->jwtService.extractEmail(accessToken) //이메일 추출
                        .ifPresent(email -> userRepository.findByUserEmail(email) //해당 이메일로 유저를 찾는다.
                                .ifPresent(this::saveAuthentication))); //해당 유저 인증 처리
        filterChain.doFilter(request, response);
    }

    /* [인증 허가 메소드]
     * 파라미터의 유저 : 우리가 만든 회원 객체 / 빌더의 유저 : UserDetails의 User 객체
     *
     * new UsernamePasswordAuthenticationToken()로 인증 객체인 Authentication 객체 생성
     * UsernamePasswordAuthenticationToken의 파라미터
     * 1. 위에서 만든 UserDetailsUser 객체 (유저 정보)
     * 2. credential(보통 비밀번호로, 인증 시에는 보통 null로 제거)
     * 3. collection < ? extends GrantedAuthority>로,
     * UserDetails의 User 객체 안에 Set<GrantedAuthority> authorities이 있어서 getter로 호출한 후에,
     * new NullAuthoritiesMapper()로 GrantedAuthoritiesMapper 객체를 생성하고 mapAuthorities()에 담기
     *
     * SecurityContextHolder.getContext()로 SecurityContext를 꺼낸 후,
     * setAuthentication()을 이용하여 위에서 만든 Authentication 객체에 대한 인증 허가 처리
     */
    private void saveAuthentication(User myUser) {
        String password = myUser.getUserPw();
        if (password == null) {
            //소셜 로그인 유저의 비밀번호 임의로 설정하여 소셜 로그인 유저도 인증되도록 설정
            password = PasswordUtil.generateRandomPassword();
        }

        UserDetails userDetailsUser = org.springframework.security.core.userdetails.User.builder()
                .username(myUser.getUserEmail())
                .password(password)
                .roles(myUser.getRole().name())
                .build();

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetailsUser, null,
                        authoritiesMapper.mapAuthorities(userDetailsUser.getAuthorities()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


}
