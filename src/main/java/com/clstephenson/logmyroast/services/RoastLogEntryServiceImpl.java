package com.clstephenson.logmyroast.services;

import com.clstephenson.logmyroast.models.RoastLogEntry;
import com.clstephenson.logmyroast.repositories.RoastLogEntryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service(value = "RoastLogEntryService")
public class RoastLogEntryServiceImpl implements RoastLogEntryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoastLogEntryServiceImpl.class);

    private RoastLogEntryRepository roastLogEntryRepository;

    public RoastLogEntryServiceImpl(RoastLogEntryRepository roastLogEntryRepository) {
        assert roastLogEntryRepository != null;
        this.roastLogEntryRepository = roastLogEntryRepository;
    }

    @Override
    public List<RoastLogEntry> findAllLogEntries() {
        return StreamSupport.stream(roastLogEntryRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<RoastLogEntry> findLogEntryById(int id) {
        return roastLogEntryRepository.findById(id);
    }

    @Override
    public RoastLogEntry save(RoastLogEntry logEntry) {
        return roastLogEntryRepository.save(logEntry);
    }

    @Override
    public void deleteLogEntryById(int id) {
        roastLogEntryRepository.deleteById(id);
    }
}
