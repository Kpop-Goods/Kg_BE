package hello.kpop.socialing.service;


import hello.kpop.socialing.Socialing;
import hello.kpop.socialing.common.ProcessUtils;
import hello.kpop.socialing.common.SocialingStatus;
import hello.kpop.socialing.exception.SocialingDataNotFoundException;
import hello.kpop.socialing.exception.SocialingNotFoundException;
import hello.kpop.socialing.exception.UnAuthorizedException;
import hello.kpop.socialing.repository.SocialingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


//소셜링 삭제

@Service
@RequiredArgsConstructor
public class SocialingDeleteService {

    private final SocialingRepository socialingRepository;
    private final UserAuthentication userAuthentication;

    @Transactional
    public void deleteSocial(Long id, Authentication authentication) {

        // 소셜링이 없으면 예외
        Socialing socialing = socialingRepository.findById(id).orElseThrow(SocialingNotFoundException::new);

        // 인증이 완료된 user 이메일
        String authEmail = userAuthentication.auth(authentication);

        String email = socialing.getUser().getUserEmail();

        //user에 수정 및 삭제 권한 체크
        if (!StringUtils.hasText(authEmail) || !authEmail.equals(email)) {
            throw new UnAuthorizedException(ProcessUtils.getMessage("Not.your.socialing", "errors"));
        }

        //이미 삭제가 된 소셜링 일때 예외
        SocialingStatus yn = socialing.getDel_yn();
        if (yn.equals(SocialingStatus.COMPLETE)) {
            throw new SocialingDataNotFoundException();
        }

        // 소셜링의 타입을 변경하고 저장
        socialing.updateType(SocialingStatus.COMPLETE);
        socialingRepository.save(socialing);
    }
}
