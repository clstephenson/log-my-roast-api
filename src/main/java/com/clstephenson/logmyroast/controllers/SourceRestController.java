package com.clstephenson.logmyroast.controllers;

import com.clstephenson.logmyroast.exceptions.SourceNotFoundException;
import com.clstephenson.logmyroast.models.Source;
import com.clstephenson.logmyroast.services.SourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SourceRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SourceRestController.class);

    private SourceService sourceService;

    public SourceRestController(SourceService sourceService) {
        assert sourceService != null;
        this.sourceService = sourceService;
    }

    @GetMapping(value = "/sources", produces = "application/json")
    public List<Source> getSources() {
        return sourceService.findAllSources();
    }

    @GetMapping(value = "/sources/{id}", produces = "application/json")
    public Source getSource(@PathVariable int id) {
        return sourceService.findSourceById(id)
                .orElseThrow(() -> new SourceNotFoundException(id));
    }

    @PostMapping(value = "/sources", produces = "application/json")
    public ResponseEntity<Source> createSource(@RequestBody Source source) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sourceService.save(source));
    }

    @PutMapping(value = "/sources/{id}", produces = "application/json")
    public ResponseEntity<Source> updateSource(@PathVariable int id, @RequestBody Source sourceInput) {
        if (sourceService.findSourceById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(sourceService.save(sourceInput));
        } else {
            throw new SourceNotFoundException(id);
        }
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/sources/{id}")
    public void deleteSource(@PathVariable int id) {
        if (sourceService.findSourceById(id).isPresent()) {
            sourceService.deleteSourceById(id);
        } else {
            throw new SourceNotFoundException(id);
        }
    }
}
