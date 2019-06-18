package com.clstephenson.logmyroast.services;

import com.clstephenson.logmyroast.models.RoastLogEntry;

import java.util.List;
import java.util.Optional;

public interface RoastLogEntryService {

    List<RoastLogEntry> findAllLogEntries();

    Optional<RoastLogEntry> findLogEntryById(int id);

    RoastLogEntry save(RoastLogEntry bean);

    void deleteLogEntryById(int id);
}
