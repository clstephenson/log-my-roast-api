package com.clstephenson.logmyroast.repositories;

import com.clstephenson.logmyroast.models.RoastLogEntry;
import org.springframework.data.repository.CrudRepository;

public interface RoastLogEntryRepository extends CrudRepository<RoastLogEntry, Integer> {
}
