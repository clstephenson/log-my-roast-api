package com.clstephenson.logmyroast.controllers;

import com.clstephenson.logmyroast.exceptions.SourceNotFoundException;
import com.clstephenson.logmyroast.models.Source;
import com.clstephenson.logmyroast.services.SourceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

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
@WebMvcTest(SourceRestController.class)
public class SourceRestControllerTest {

    private final String API_BASE = "/api/sources";
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private SourceService sourceService;
    private Source testSource1;
    private Source testSource2;
    private List<Source> sources;

    @Before
    public void setUp() throws Exception {
        testSource1 = new Source("Name");
        testSource1.setId(1);
        testSource2 = new Source("Another Name");
        testSource2.setId(2);
        sources = Arrays.asList(testSource1, testSource2);
    }

    @Test
    public void getSources_returns200AndSources() throws Exception {
        when(sourceService.findAllSources()).thenReturn(sources);
        mvc.perform(get(API_BASE)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(sources.get(0).getName())));

        verify(sourceService, times(1)).findAllSources();
    }

    @Test
    public void getSource_whenFound_returns200AndOneSource() throws Exception {
        when(sourceService.findSourceById(1)).thenReturn(Optional.of(testSource1));
        mvc.perform(get(API_BASE + "/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(testSource1.getName())));

        verify(sourceService, times(1)).findSourceById(anyInt());
    }

    @Test
    public void getSource_whenNotFound_returns404() throws Exception {
        when(sourceService.findSourceById(anyInt())).thenThrow(SourceNotFoundException.class);
        mvc.perform(get(API_BASE + "/{id}", 1))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(sourceService, times(1)).findSourceById(anyInt());
    }

    @Test
    public void createSource_whenSuccessful_returns201AndSource() throws Exception {
        when(sourceService.save(any(Source.class))).then(returnsFirstArg());
        mvc.perform(post(API_BASE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testSource1)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(testSource1.getName())));

        verify(sourceService, times(1)).save(any(Source.class));
    }

    @Test
    public void updateSource_whenSuccessful_returns200AndSource() throws Exception {
        when(sourceService.findSourceById(anyInt())).thenReturn(Optional.of(testSource1));
        when(sourceService.save(any(Source.class))).then(returnsFirstArg());
        mvc.perform(put(API_BASE + "/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testSource1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(testSource1.getName())));

        verify(sourceService, times(1)).findSourceById(anyInt());
        verify(sourceService, times(1)).save(any(Source.class));
    }

    @Test
    public void updateSource_whenIdNotFound_returns404() throws Exception {
        when(sourceService.save(any(Source.class))).thenThrow(SourceNotFoundException.class);
        String sourceInJson = "{\"name\":\"new source\"}";
        mvc.perform(put(API_BASE + "/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(sourceInJson))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(sourceService, times(1)).findSourceById(anyInt());
        verify(sourceService, times(0)).save(any(Source.class));
    }

    @Test
    public void deleteSource_whenSuccessful_returns204() throws Exception {
        when(sourceService.findSourceById(anyInt())).thenReturn(Optional.of(testSource1));
        doNothing().when(sourceService).deleteSourceById(anyInt());
        mvc.perform(delete(API_BASE + "/{id}", 1))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(sourceService, times(1)).findSourceById(anyInt());
        verify(sourceService, times(1)).deleteSourceById(anyInt());
    }

    @Test
    public void deleteSource_whenIdNotFound_returns404() throws Exception {
        when(sourceService.findSourceById(anyInt())).thenReturn(Optional.empty());
        doNothing().when(sourceService).deleteSourceById(anyInt());
        mvc.perform(delete(API_BASE + "/{id}", 1))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(sourceService, times(1)).findSourceById(anyInt());
        verify(sourceService, times(0)).deleteSourceById(anyInt());
    }
}