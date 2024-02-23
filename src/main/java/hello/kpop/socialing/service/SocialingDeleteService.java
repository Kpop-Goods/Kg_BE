package hello.kpop.socialing.service;


import hello.kpop.socialing.Socialing;
import hello.kpop.socialing.SocialingStatus;
import hello.kpop.socialing.exception.SocialingNotFoundException;
import hello.kpop.socialing.repository.SocialingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


//소셜링 삭제

@Service
@RequiredArgsConstructor
public class SocialingDeleteService {

    private final SocialingRepository socialingRepository;

    @Transactional
    public void deleteSocial(Long id) {
        // Id에 해당하는 소셜링을 찾습니다.
        Socialing socialing = socialingRepository.findById(id).orElseThrow(SocialingNotFoundException::new);

        // 소셜링의 타입을 변경하고 저장
        socialing.updateType(SocialingStatus.COMPLETE);
        socialingRepository.save(socialing);
    }
}
