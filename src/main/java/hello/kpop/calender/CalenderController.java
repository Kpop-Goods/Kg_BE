package hello.kpop.calender;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CalenderController {

    private final CalenderService calService;

    @PostMapping("/calenders/new") // Create
    public String create(@RequestBody CalenderDto calenderDto) {
        calService.create(calenderDto);
        return "calender is registered";
    }

    @GetMapping("/calenders/{calId}") // Read
    public Optional<Calender> read(@PathVariable("calId") Long calId) {
        return calService.read(calId);
    }

    @PatchMapping(value = "/calenders/{calId}") // Update
    public Boolean update(@PathVariable("calId") Long calId, @RequestBody CalenderDto calenderDto) {
        return calService.update(calId, calenderDto);
    }

    @DeleteMapping(value = "/calenders/{calId}") // Delete
    public @ResponseBody
    ResponseEntity<Long> delete(@PathVariable("calId") Long calId) {
        if (!calService.delete(calId))
            return new ResponseEntity<>(calId, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(calId, HttpStatus.OK);
    }
}
