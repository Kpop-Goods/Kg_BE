package hello.kpop.calendar;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarService calService;

    @PostMapping("/calendars/new") // Create
    public String create(@RequestBody CalendarDto calendarDto) {
        calService.create(calendarDto);
        return "calendar is registered";
    }

    @GetMapping("/calendars/{calId}") // Read
    public Optional<Calendar> read(@PathVariable("calId") Long calId) {
        return calService.read(calId);
    }

    @PatchMapping(value = "/calendars/{calId}") // Update
    public Boolean update(@PathVariable("calId") Long calId, @RequestBody CalendarDto calendarDto) {
        return calService.update(calId, calendarDto);
    }

    @DeleteMapping(value = "/calendars/{calId}") // Delete
    public @ResponseBody
    ResponseEntity<Long> delete(@PathVariable("calId") Long calId) {
        if (!calService.delete(calId))
            return new ResponseEntity<>(calId, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(calId, HttpStatus.OK);
    }
}
