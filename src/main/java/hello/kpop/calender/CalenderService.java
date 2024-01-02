package hello.kpop.calender;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CalenderService {

    private final CalenderRepository calRepository;

    @Transactional
    public void create(CalenderDto dto) { // Create
        Calender calender = Calender.builder()
                .id(dto.getId())
                .name(dto.getName())
                .artist(dto.getArtist())
                .start(dto.getStart())
                .end(dto.getEnd())
                .link(dto.getLink())
                .meta(dto.getMeta())
                .build();

        calRepository.save(calender);
    }

    public Optional<Calender> read(Long calId) { // Read
        return calRepository.findById(calId);
    }

    public Boolean update(Long calId, CalenderDto dto) { // Update
        Optional<Calender> cal = calRepository.findById(calId);
        if (cal.isEmpty()) return false;

        Calender calender = Calender.builder()
        //      .id(dto.getId())
                .name(dto.getName())
                .artist(dto.getArtist())
                .start(dto.getStart())
                .end(dto.getEnd())
                .link(dto.getLink())
                .meta(dto.getMeta())
                .build();
        calRepository.save(calender);
        return true;
    }

    public Boolean delete(Long calId) { // Delete
        Optional<Calender> cal = calRepository.findById(calId);
        if (cal.isEmpty()) return false;

        calRepository.deleteById(calId);
        return true;
    }
}
