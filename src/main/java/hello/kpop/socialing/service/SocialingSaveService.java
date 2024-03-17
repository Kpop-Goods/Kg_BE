package hello.kpop.socialing.service;


import hello.kpop.artist.Artist;
import hello.kpop.artist.repository.ArtistRepository;
import hello.kpop.socialing.Socialing;
import hello.kpop.socialing.common.ProcessUtils;
import hello.kpop.socialing.dto.SocialingData;
import hello.kpop.socialing.exception.BadRequestException;
import hello.kpop.socialing.exception.SocialingNotFoundException;
import hello.kpop.socialing.exception.UnAuthorizedException;
import hello.kpop.socialing.repository.SocialingRepository;
import hello.kpop.socialing.validator.SocialingValidator;
import hello.kpop.user.User;
import hello.kpop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

import java.util.Optional;


//소셜링 등록과 수정
@Service
@RequiredArgsConstructor
public class SocialingSaveService {

    private final SocialingRepository socialingRepository;
    private final ArtistRepository artistRepository;
    private final SocialingValidator socialingValidator;
    private final UserRepository userRepository;
    private final UserAuthentication userAuthentication;


    // 소셜 등록
    @Transactional
   public void createSocial(SocialingData data, Errors errors, Authentication authentication) {
            socialingValidator.validate(data, errors);
            if(errors.hasErrors()){
                return;
            }
            save(data,authentication);
        }



    //소셜 수정
    @Transactional
    public void updateSocial(Long id, SocialingData data ,Errors errors, Authentication authentication){

        // 소셜링이 없으면 예외
        Socialing socialing = socialingRepository.findById(id).orElseThrow(SocialingNotFoundException::new);

        // 인증이 완료된 user 이메일
        String authEmail = userAuthentication.auth(authentication);

        String email = socialing.getUser().getUserEmail();

        //user에 수정 및 삭제 권한 체크
        if (!StringUtils.hasText(authEmail) || !authEmail.equals(email)) {
            throw new UnAuthorizedException(ProcessUtils.getMessage("Not.your.socialing", "errors"));
        }
        socialingValidator.validate(data,errors);
        if(errors.hasErrors()){
            return ;
        }
        Optional<Artist> artist = artistRepository.findByArtistName(data.getArtist());
        if(artist.isPresent()) {
            socialing.modify(data, artist.get());
        }else {
            throw new BadRequestException(ProcessUtils.getMessage("Request.Artist" , "errors"));
        }
    }
    @Transactional
    public void save(SocialingData data, Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByUserEmail(userDetails.getUsername()).orElseThrow(null);

        Optional<Artist> artist = artistRepository.findByArtistName(data.getArtist());
        if (artist.isPresent()) {
           Socialing socialing = Socialing.builder()
                    .user(user)
                    .artist(artist.get())
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

            data.setNickname(user.getNickname());
            socialingRepository.saveAndFlush(socialing);
        } else {
            throw new BadRequestException(ProcessUtils.getMessage("Request.Artist" , "errors"));
        }
    }
}
