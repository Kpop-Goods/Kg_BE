package hello.kpop.socialing.service;


import hello.kpop.artist.Artist;
import hello.kpop.artist.repository.ArtistRepository;
import hello.kpop.follow.FollowService;
import hello.kpop.socialing.common.ProcessUtils;
import hello.kpop.socialing.common.UserAuthentication;
import hello.kpop.socialing.common.exception.BadRequestException;
import hello.kpop.socialing.dto.SocialingData;
import hello.kpop.socialing.entity.Socialing;
import hello.kpop.socialing.exception.SocialingNotFoundException;
import hello.kpop.socialing.repository.SocialingRepository;
import hello.kpop.socialing.validator.SocialingValidator;
import hello.kpop.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.Optional;


//소셜링 등록과 수정
@Service
@RequiredArgsConstructor
public class SocialingSaveService {

    private final SocialingRepository socialingRepository;
    private final ArtistRepository artistRepository;
    private final SocialingValidator socialingValidator;
    private final UserAuthentication userAuthentication;
	private final FollowService followService; // follow service by neo4j

    // 소셜 등록
    public void createSocial(SocialingData data, Errors errors, Authentication authentication) {
        socialingValidator.validate(data, errors);
        if(errors.hasErrors()){
            return;
        }
        save(data,authentication);
    }

    //소셜 수정
    public void updateSocial(Long id, SocialingData data ,Errors errors, Authentication authentication){

        // 소셜링이 없으면 예외
        Socialing socialing = socialingRepository.findById(id).orElseThrow(SocialingNotFoundException::new);

        //등록된 유저 권한 체크
        String email = socialing.getUser().getUserEmail();
        userAuthentication.checkUser(email,authentication);
        data.setNickname(socialing.getUser().getNickname());

        socialingValidator.validate(data,errors);
        if(errors.hasErrors()){
            return ;
        }

        Artist artist = getArtist(data.getArtistName());
        socialing.modify(data,artist);

		followService.modifyMeet(socialing.getSocialingId(),
			socialing.getSocialing_name(), artist.getArtistId(), socialing.getUser().getId()); // modify a meet node
	}

    //등록 메서드
    public void save(SocialingData data, Authentication authentication) {

        User user = userAuthentication.getUser(authentication);

        //    String email = userAuthentication.auth(authentication);
        //    User user = userRepository.findByUserEmail(email).orElseThrow(null);

        Artist artist= getArtist(data.getArtistName());

        Socialing socialing = Socialing.builder()
                .user(user)
                .artist(artist)
                .socialing_name(data.getSocialing_name())
                .socialing_content(data.getSocialing_content())
                .type(data.getType())
                .social_place(data.getSocial_place())
                .start_date(data.getStart_date())
                .end_date(data.getEnd_date())
                .quota(data.getQuota())
                .chat_url(data.getChat_url())
                .del_yn(data.getDel_yn())
                .build();
        socialingRepository.saveAndFlush(socialing);
        //응답시 유저 닉네임 보여주기
        data.setNickname(user.getNickname());

		followService.addMeet(socialing.getSocialingId(),
			socialing.getSocialing_name(), artist.getArtistId(), user.getId()); // add a meet node
	}

    //등록할 아티스트 확인
    public Artist getArtist(String artistName){
        Optional<Artist> artist = artistRepository.findByArtistName(artistName);
        if(artist.isPresent()) {
            return artist.get();
        }else {
            throw new BadRequestException(ProcessUtils.getMessage("Request.Artist" , "errors"));
        }

    }
}

