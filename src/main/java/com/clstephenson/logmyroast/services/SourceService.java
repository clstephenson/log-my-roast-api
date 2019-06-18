package com.clstephenson.logmyroast.services;

import com.clstephenson.logmyroast.models.Source;

import java.util.List;
import java.util.Optional;

public interface SourceService {

    List<Source> findAllSources();

    Optional<Source> findSourceById(int id);

    Source save(Source source);

    void deleteSourceById(int id);
}
