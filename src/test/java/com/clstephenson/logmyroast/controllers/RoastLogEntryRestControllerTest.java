package com.clstephenson.logmyroast.controllers;

import com.clstephenson.logmyroast.exceptions.RoastLogEntryNotFoundException;
import com.clstephenson.logmyroast.models.RoastLogEntry;
import com.clstephenson.logmyroast.services.RoastLogEntryService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RoastLogEntryRestController.class)
public class RoastLogEntryRestControllerTest {

    private final String API_BASE = "/api/entries";
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private RoastLogEntryService roastLogEntryService;
    private RoastLogEntry testLogEntry1;
    private RoastLogEntry testLogEntry2;
    private List<RoastLogEntry> roastLogEntries;

    @Before
    public void setUp() throws Exception {
        objectMapper.disable(SerializationFeature.WRITE_DATES_WITH_ZONE_ID);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        testLogEntry1 = new RoastLogEntry(ZonedDateTime.now());
        testLogEntry1.setId(1);
        testLogEntry2 = new RoastLogEntry(ZonedDateTime.now());
        testLogEntry2.setId(2);
        roastLogEntries = Arrays.asList(testLogEntry1, testLogEntry2);
    }

    @Test
    public void getLogEntries_returns200AndLogEntries() throws Exception {
        when(roastLogEntryService.findAllLogEntries()).thenReturn(roastLogEntries);
        mvc.perform(get(API_BASE)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].roastDate", is(roastLogEntries.get(0).getRoastDate().toOffsetDateTime().toString())));

        verify(roastLogEntryService, times(1)).findAllLogEntries();
    }

    @Test
    public void getLogEntry_whenFound_returns200AndOneLogEntry() throws Exception {
        when(roastLogEntryService.findLogEntryById(1)).thenReturn(Optional.of(testLogEntry1));
        mvc.perform(get(API_BASE + "/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roastDate", is(testLogEntry1.getRoastDate().toOffsetDateTime().toString())));

        verify(roastLogEntryService, times(1)).findLogEntryById(anyInt());
    }

    @Test
    public void getLogEntry_whenNotFound_returns404() throws Exception {
        when(roastLogEntryService.findLogEntryById(anyInt())).thenThrow(RoastLogEntryNotFoundException.class);
        mvc.perform(get(API_BASE + "/{id}", 1))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(roastLogEntryService, times(1)).findLogEntryById(anyInt());
    }

    @Test
    public void createLogEntry_whenSuccessful_returns201AndLogEntry() throws Exception {
        when(roastLogEntryService.save(any(RoastLogEntry.class))).then(returnsFirstArg());
        mvc.perform(post(API_BASE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testLogEntry1)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.roastDate", is(testLogEntry1.getRoastDate().toOffsetDateTime().toString())));

        verify(roastLogEntryService, times(1)).save(any(RoastLogEntry.class));
    }

    @Test
    public void updateLogEntry_whenSuccessful_returns200AndLogEntry() throws Exception {
        when(roastLogEntryService.findLogEntryById(anyInt())).thenReturn(Optional.of(testLogEntry1));
        when(roastLogEntryService.save(any(RoastLogEntry.class))).then(returnsFirstArg());
        mvc.perform(put(API_BASE + "/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testLogEntry1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roastDate", is(testLogEntry1.getRoastDate().toOffsetDateTime().toString())));

        verify(roastLogEntryService, times(1)).findLogEntryById(anyInt());
        verify(roastLogEntryService, times(1)).save(any(RoastLogEntry.class));
    }

    @Test
    public void updateLogEntry_whenIdNotFound_returns404() throws Exception {
        when(roastLogEntryService.save(any(RoastLogEntry.class))).thenThrow(RoastLogEntryNotFoundException.class);
        String logEntryInJson = "{\"roastDate\":\"2019-06-14T18:33:44.644Z\"}";
        mvc.perform(put(API_BASE + "/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(logEntryInJson))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(roastLogEntryService, times(1)).findLogEntryById(anyInt());
        verify(roastLogEntryService, times(0)).save(any(RoastLogEntry.class));
    }

    @Test
    public void deleteLogEntry_whenSuccessful_returns204() throws Exception {
        when(roastLogEntryService.findLogEntryById(anyInt())).thenReturn(Optional.of(testLogEntry1));
        doNothing().when(roastLogEntryService).deleteLogEntryById(anyInt());
        mvc.perform(delete(API_BASE + "/{id}", 1))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(roastLogEntryService, times(1)).findLogEntryById(anyInt());
        verify(roastLogEntryService, times(1)).deleteLogEntryById(anyInt());
    }

    @Test
    public void deleteLogEntry_whenIdNotFound_returns404() throws Exception {
        when(roastLogEntryService.findLogEntryById(anyInt())).thenReturn(Optional.empty());
        doNothing().when(roastLogEntryService).deleteLogEntryById(anyInt());
        mvc.perform(delete(API_BASE + "/{id}", 1))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(roastLogEntryService, times(1)).findLogEntryById(anyInt());
        verify(roastLogEntryService, times(0)).deleteLogEntryById(anyInt());
    }
}