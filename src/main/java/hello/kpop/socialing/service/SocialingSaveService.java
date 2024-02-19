package hello.kpop.socialing.service;


import hello.kpop.socialing.Socialing;
import hello.kpop.socialing.dto.SocialingRequestDto;
import hello.kpop.socialing.dto.SocialingResponseDto;
import hello.kpop.socialing.exception.SocialingNotFoundException;
import hello.kpop.socialing.repository.SocialingRepository;
import hello.kpop.socialing.validator.SocialingValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;


//소셜링 등록과 수정
@Service
@RequiredArgsConstructor
public class SocialingSaveService {

    private final SocialingRepository socialingRepository;
    private final SocialingValidator socialingValidator;



    // 소셜 등록
    public SocialingResponseDto createSocial(SocialingRequestDto socialingDto, BindingResult bindingResult) {

        socialingValidator.validate(socialingDto , bindingResult);
//        if(bindingResult.hasErrors()){
//            System.out.println("에러는"+bindingResult);
//        }
        Socialing socialing = new Socialing(socialingDto);
        socialingRepository.save(socialing);
        return new SocialingResponseDto(socialing);
    }


    //소셜 수정
    @Transactional
    public SocialingResponseDto updateSocial(Long id, SocialingRequestDto socialingDto){

        Socialing socialing = socialingRepository.findById(id).orElseThrow(SocialingNotFoundException::new);
        socialing.modify(socialingDto);
        return new SocialingResponseDto(socialing);
    }





}
