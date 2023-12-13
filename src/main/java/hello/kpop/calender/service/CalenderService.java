package hello.kpop.calender.service;

import hello.kpop.calender.Calender;
import hello.kpop.calender.dto.CalenderDto;
import hello.kpop.calender.repository.CalenderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CalenderService {

    private final CalenderRepository calenderRepository;

    @Transactional
    public void signUp(CalenderDto calenderDto) throws Exception {

        Calender calender = Calender.builder()
                .artistId(calenderDto.getArtistId())
                .calenderDate(calenderDto.getCalenderDate())
                .calenderName(calenderDto.getCalenderName())
                .calenderLink(calenderDto.getCalenderLink())
                .build();

        calenderRepository.save(calender);
    }

}
