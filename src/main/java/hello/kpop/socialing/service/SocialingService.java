package hello.kpop.socialing.service;

import hello.kpop.socialing.Socialing;
import hello.kpop.socialing.dto.SocialingDto;
import hello.kpop.socialing.repository.SocialingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SocialingService {

    private final SocialingRepository socialingRepository;

    @Transactional
    public void signUp(SocialingDto socialingDto) throws Exception {

        Socialing socialing = Socialing.builder()
                .artistId(socialingDto.getArtistId())
                .socialDate(socialingDto.getSocialDate())
                .socialNop(socialingDto.getSocialNop())
                .socialPlace(socialingDto.getSocialPlace())
                .socialTitle(socialingDto.getSocialTitle())
                .socialMoney(socialingDto.getSocialMoney())
                .socialLink(socialingDto.getSocialLink())
                .socialHeart(socialingDto.getSocialHeart())
                .build();

        socialingRepository.save(socialing);
    }
}
