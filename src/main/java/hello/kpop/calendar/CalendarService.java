package hello.kpop.calendar;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRepository calRepository;

    @Transactional
    public void create(CalendarDto dto) { // Create
        Calendar calendar = Calendar.builder()
                .id(dto.getId())
                .name(dto.getName())
                .artist(dto.getArtist())
                .start(dto.getStart())
                .end(dto.getEnd())
                .link(dto.getLink())
                .meta(dto.getMeta())
                .build();

        calRepository.save(calendar);
    }

    public Optional<Calendar> read(Long calId) { // Read
        return calRepository.findById(calId);
    }

    public Boolean update(Long calId, CalendarDto dto) { // Update
        Optional<Calendar> cal = calRepository.findById(calId);
        if (cal.isEmpty()) return false;

        Calendar calendar = Calendar.builder()
        //      .id(dto.getId())
                .name(dto.getName())
                .artist(dto.getArtist())
                .start(dto.getStart())
                .end(dto.getEnd())
                .link(dto.getLink())
                .meta(dto.getMeta())
                .build();
        calRepository.save(calendar);
        return true;
    }

    public Boolean delete(Long calId) { // Delete
        Optional<Calendar> cal = calRepository.findById(calId);
        if (cal.isEmpty()) return false;

        calRepository.deleteById(calId);
        return true;
    }
}
