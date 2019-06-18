package com.clstephenson.logmyroast.services;

import com.clstephenson.logmyroast.models.RoastLogEntry;
import com.clstephenson.logmyroast.repositories.RoastLogEntryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RoastLogEntryServiceImplTest {

    private static final ZonedDateTime LOG_ENTRY_1_DATE = ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("UTC"));
    private static final ZonedDateTime LOG_ENTRY_2_DATE = LOG_ENTRY_1_DATE.plus(2, ChronoUnit.DAYS);
    @Mock
    RoastLogEntryRepository roastLogEntryRepository;
    @InjectMocks
    RoastLogEntryServiceImpl roastLogEntryService;
    RoastLogEntry logEntry1;
    RoastLogEntry logEntry2;
    List<RoastLogEntry> logEntries;

    @Before
    public void setup() {
        logEntry1 = new RoastLogEntry(LOG_ENTRY_1_DATE);
        logEntry2 = new RoastLogEntry(LOG_ENTRY_2_DATE);
        logEntries = Arrays.asList(logEntry1, logEntry2);
    }

    @Test
    public void findAllLogEntries_ReturnListOfEntries() {
        when(roastLogEntryRepository.findAll()).thenReturn(logEntries);
        assertThat(roastLogEntryService.findAllLogEntries(), equalTo(logEntries));

        verify(roastLogEntryRepository, times(1)).findAll();
    }

    @Test
    public void findLogEntryById_ReturnLogEntry() {
        when(roastLogEntryRepository.findById(anyInt())).thenReturn(Optional.of(logEntry1));
        assertThat(roastLogEntryService.findLogEntryById(1).get(), equalTo(logEntry1));

        verify(roastLogEntryRepository, times(1)).findById(anyInt());
    }

    @Test
    public void save_ReturnSavedLogEntry() {
        final ZonedDateTime LOG_ENTRY_3_DATE = LOG_ENTRY_1_DATE.plus(5, ChronoUnit.DAYS);
        RoastLogEntry logEntry = new RoastLogEntry(LOG_ENTRY_3_DATE);
        when(roastLogEntryRepository.save(logEntry)).thenReturn(logEntry);
        assertThat(roastLogEntryService.save(logEntry), equalTo(logEntry));

        verify(roastLogEntryRepository, times(1)).save(any(RoastLogEntry.class));
    }

    @Test
    public void deleteById_repositoryMethodCalled() {
        doNothing().when(roastLogEntryRepository).deleteById(anyInt());
        roastLogEntryService.deleteLogEntryById(1);
        verify(roastLogEntryRepository, times(1)).deleteById(anyInt());
    }
}