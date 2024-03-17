package hello.kpop.calendar;

import hello.kpop.artist.Artist;
import hello.kpop.artist.repository.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRepository calRepository;
    private final ArtistRepository artistRepository;

    @Transactional
    public Optional<Calendar> create(CalendarDto dto) { // Create

        Optional<Artist> optArtist = artistRepository.findById(dto.getArtistId());
        if (optArtist.isEmpty()) return null; // failed

        Calendar calendar = Calendar.builder()
                .id(dto.getId()) //ignore
                .name(dto.getName())
                .artist(optArtist.get())
                .start(dto.getStart()).end(dto.getEnd())
                .link(dto.getLink()).meta(dto.getMeta()).build();

        return Optional.of(calRepository.save(calendar));
    }

    public Optional<Calendar> read(Long calId) { // Read

        return calRepository.findById(calId);

    }

    public List<Calendar> readFromTo(Long calId, LocalDate start, LocalDate ended) { // Read

        Date dateStart = java.sql.Date.valueOf(start);
        Date dateEnded = java.sql.Date.valueOf(ended);

        return calRepository.findAllByStartBetween(dateStart, dateEnded);
	/*
		List<Calendar> list = calRepository.findAllByStartBetween(dateStart, dateEnded);
		Calendar cal = null;
		for (int i = 0; i<list.size(); i++) {
			cal = list.get(i);
			System.out.printf("\t%s - (%s, %s)\n",
				cal.getName(), cal.getStart().toString(), cal.getEnd().toString());
		}

		return list;
	*/
    }

    public Boolean update(Long calId, CalendarDto dto) { // Update
        Optional<Calendar> optCal = calRepository.findById(calId);
        if (optCal.isEmpty()) return false;
        System.out.printf("findById %d\n", calId);
        Calendar cal = optCal.get();

        if (dto.getArtistId() != cal.getArtist().getArtistId())
            return false; // calId != cal.getId() checked by findById()

/*      Calendar calendar = Calendar.builder()
                .id(calId) 				 // use calId not dto's id
				.artist(cal.getArtist()) // reuse original artist
                .name(dto.getName())	 // next items can be modified
                .start(dto.getStart())
                .end(dto.getEnd())
                .link(dto.getLink())
                .meta(dto.getMeta())
                .build();
		System.out.printf("Calendar builder %d\n", dto.getId());
        Calendar one = calRepository.save(calendar);
   		System.out.printf("Calendar Repo saved\n");
		System.out.printf("%d - %s %s", one.getId(), one.getName(), one.getMeta());
*/
        cal.setName(dto.getName());
        cal.setStart(dto.getStart());
        cal.setEnd(dto.getEnd());
        cal.setLink(dto.getLink());
        cal.setMeta(dto.getMeta());

//		Calendar one = calRepository.save(cal);
        calRepository.save(cal);
        return true;
    }

    public Boolean delete(Long calId) { // Delete
        Optional<Calendar> cal = calRepository.findById(calId);
        if (cal.isEmpty()) return false;

        calRepository.deleteById(calId);
        return true;
    }
}
