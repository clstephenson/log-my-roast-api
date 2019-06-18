package com.clstephenson.logmyroast.services;

import com.clstephenson.logmyroast.models.Source;
import com.clstephenson.logmyroast.repositories.SourceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service(value = "SourceService")
public class SourceServiceImpl implements SourceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SourceServiceImpl.class);

    private SourceRepository sourceRepository;

    public SourceServiceImpl(SourceRepository sourceRepository) {
        assert sourceRepository != null;
        this.sourceRepository = sourceRepository;
    }

    @Override
    public List<Source> findAllSources() {
        return StreamSupport.stream(sourceRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Source> findSourceById(int id) {
        return sourceRepository.findById(id);
    }

    @Override
    public Source save(Source source) {
        return sourceRepository.save(source);
    }

    @Override
    public void deleteSourceById(int id) {
        sourceRepository.deleteById(id);
    }
}
