package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {

    private TimeEntryRepository timeEntryRepository;

    @Autowired
    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }

    @PostMapping
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry timeEntry = timeEntryRepository.create(timeEntryToCreate);
        return new ResponseEntity<TimeEntry>(timeEntry, HttpStatus.CREATED);
    }

    @GetMapping("{timeEntryId}")
    public ResponseEntity<TimeEntry> read(@PathVariable long timeEntryId) {
        TimeEntry timeEntry = timeEntryRepository.find(timeEntryId);
        if(timeEntry == null) {
            return new ResponseEntity<TimeEntry>(timeEntry, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<TimeEntry>(timeEntry, HttpStatus.OK);
    }


    @PutMapping("{timeEntryId}")
    public ResponseEntity<TimeEntry> update(@PathVariable long timeEntryId, @RequestBody TimeEntry timeEntry) {
        TimeEntry timeEntryFromDb = timeEntryRepository.update(timeEntryId, timeEntry);
        if(timeEntryFromDb == null) {
            return new ResponseEntity<TimeEntry>(timeEntryFromDb, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<TimeEntry>(timeEntryFromDb, HttpStatus.OK);
    }

    @DeleteMapping("{timeEntryId}")
    public ResponseEntity<Void> delete(@PathVariable long timeEntryId) {

        timeEntryRepository.delete(timeEntryId);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }


    @GetMapping()
    public ResponseEntity<List<TimeEntry>> list() {
        return new ResponseEntity<List<TimeEntry>>(timeEntryRepository.list(), HttpStatus.OK);
    }
}
