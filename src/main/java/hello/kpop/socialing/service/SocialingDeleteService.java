package hello.kpop.socialing.service;


import hello.kpop.socialing.Socialing;
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

    //소셜 삭제 (타입으로 삭제 말고 바뀌게 )
    @Transactional
    public void deleteSocial(Long Id){
        //삭제시 Id가 없으면 예외 발생
        Socialing socialing= socialingRepository.findById(Id).orElseThrow(SocialingNotFoundException::new);
        //있으면 삭제
        socialingRepository.delete(socialing);
        socialingRepository.flush();
    }
}
