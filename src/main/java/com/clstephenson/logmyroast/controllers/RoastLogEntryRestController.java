package com.clstephenson.logmyroast.controllers;

import com.clstephenson.logmyroast.exceptions.RoastLogEntryNotFoundException;
import com.clstephenson.logmyroast.models.RoastLogEntry;
import com.clstephenson.logmyroast.services.RoastLogEntryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RoastLogEntryRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoastLogEntryRestController.class);

    private RoastLogEntryService roastLogEntryService;

    public RoastLogEntryRestController(RoastLogEntryService roastLogEntryService) {
        assert roastLogEntryService != null;
        this.roastLogEntryService = roastLogEntryService;
    }

    @GetMapping(value = "/entries", produces = "application/json")
    public List<RoastLogEntry> getRoastLogEntries() {
        return roastLogEntryService.findAllLogEntries();
    }

    @GetMapping(value = "/entries/{id}", produces = "application/json")
    public RoastLogEntry getRoastLogEntry(@PathVariable int id) {
        return roastLogEntryService.findLogEntryById(id)
                .orElseThrow(() -> new RoastLogEntryNotFoundException(id));
    }

    @PostMapping(value = "/entries", produces = "application/json")
    public ResponseEntity<RoastLogEntry> createRoastLogEntry(@RequestBody RoastLogEntry roastLogEntry) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roastLogEntryService.save(roastLogEntry));
    }

    @PutMapping(value = "/entries/{id}", produces = "application/json")
    public ResponseEntity<RoastLogEntry> updateRoastLogEntry(@PathVariable int id, @RequestBody RoastLogEntry roastLogEntryInput) {
        if (roastLogEntryService.findLogEntryById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(roastLogEntryService.save(roastLogEntryInput));
        } else {
            throw new RoastLogEntryNotFoundException(id);
        }
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/entries/{id}")
    public void deleteRoastLogEntry(@PathVariable int id) {
        if (roastLogEntryService.findLogEntryById(id).isPresent()) {
            roastLogEntryService.deleteLogEntryById(id);
        } else {
            throw new RoastLogEntryNotFoundException(id);
        }
    }
}
