package hello.kpop.socialing.service;


import hello.kpop.follow.FollowService;
import hello.kpop.socialing.SocialingStatus;
import hello.kpop.socialing.common.UserAuthentication;
import hello.kpop.socialing.entity.Socialing;
import hello.kpop.socialing.exception.SocialingDataNotFoundException;
import hello.kpop.socialing.exception.SocialingNotFoundException;
import hello.kpop.socialing.repository.SocialingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


//소셜링 삭제

@Service
@RequiredArgsConstructor
public class SocialingDeleteService {

    private final SocialingRepository socialingRepository;
    private final UserAuthentication userAuthentication;
	private final FollowService followService; // follow service by neo4j

    @Transactional
    public void deleteSocial(Long id, Authentication authentication) {

        // 소셜링이 없으면 예외
        Socialing socialing = socialingRepository.findById(id).orElseThrow(SocialingNotFoundException::new);

        //등록된 유저 권한 체크
        String email = socialing.getUser().getUserEmail();
        userAuthentication.checkUser(email,authentication);

        //이미 삭제가 된 소셜링 일때 예외
        SocialingStatus yn = socialing.getDel_yn();
        if (yn.equals(SocialingStatus.COMPLETE)) {
            throw new SocialingDataNotFoundException();
        }

        // 소셜링의 타입을 변경하고 저장
        socialing.updateType(SocialingStatus.COMPLETE);
        socialingRepository.save(socialing);

		followService.deleteMeet(socialing.getSocialingId()); // delete a meet node
	}
}
