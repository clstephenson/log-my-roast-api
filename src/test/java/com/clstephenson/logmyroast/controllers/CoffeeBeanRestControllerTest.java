package com.clstephenson.logmyroast.controllers;

import com.clstephenson.logmyroast.exceptions.CoffeeBeanNotFoundException;
import com.clstephenson.logmyroast.models.CoffeeBean;
import com.clstephenson.logmyroast.models.Origin;
import com.clstephenson.logmyroast.services.CoffeeBeanService;
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
@WebMvcTest(CoffeeBeanRestController.class)
public class CoffeeBeanRestControllerTest {

    private final String API_BASE = "/api/coffeebeans";
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CoffeeBeanService coffeeBeanService;
    private CoffeeBean testBean1;
    private CoffeeBean testBean2;
    private List<CoffeeBean> coffeeBeans;

    @Before
    public void setUp() throws Exception {
        testBean1 = new CoffeeBean(Origin.COLUMBIA, "Farm 1");
        testBean1.setId(1);
        testBean2 = new CoffeeBean(Origin.ETHIOPIA, "Farm 2");
        testBean2.setId(2);
        coffeeBeans = Arrays.asList(testBean1, testBean2);
    }

    @Test
    public void getCoffeeBeans_returns200AndCoffeeBeans() throws Exception {
        when(coffeeBeanService.findAllCoffeeBeans()).thenReturn(coffeeBeans);
        mvc.perform(get(API_BASE)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].farm", is(testBean1.getFarm())));

        verify(coffeeBeanService, times(1)).findAllCoffeeBeans();
    }

    @Test
    public void getCoffeeBean_whenFound_returns200AndOneCoffeeBean() throws Exception {
        when(coffeeBeanService.findCoffeeBeanById(1)).thenReturn(Optional.of(testBean1));
        mvc.perform(get(API_BASE + "/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.farm", is(testBean1.getFarm())));

        verify(coffeeBeanService, times(1)).findCoffeeBeanById(anyInt());
    }

    @Test
    public void getCoffeeBean_whenNotFound_returns404() throws Exception {
        when(coffeeBeanService.findCoffeeBeanById(anyInt())).thenThrow(CoffeeBeanNotFoundException.class);
        mvc.perform(get(API_BASE + "/{id}", 1))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(coffeeBeanService, times(1)).findCoffeeBeanById(anyInt());
    }

    @Test
    public void createCoffeeBean_whenSuccessful_returns201AndCoffeeBean() throws Exception {
        when(coffeeBeanService.save(any(CoffeeBean.class))).then(returnsFirstArg());
        mvc.perform(post(API_BASE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testBean1)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.farm", is(testBean1.getFarm())));

        verify(coffeeBeanService, times(1)).save(any(CoffeeBean.class));
    }

    @Test
    public void updateCoffeeBean_whenSuccessful_returns200AndCoffeeBean() throws Exception {
        when(coffeeBeanService.findCoffeeBeanById(anyInt())).thenReturn(Optional.of(testBean1));
        when(coffeeBeanService.save(any(CoffeeBean.class))).then(returnsFirstArg());
        mvc.perform(put(API_BASE + "/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testBean1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.farm", is(testBean1.getFarm())));

        verify(coffeeBeanService, times(1)).findCoffeeBeanById(anyInt());
        verify(coffeeBeanService, times(1)).save(any(CoffeeBean.class));
    }

    @Test
    public void updateCoffeeBean_whenIdNotFound_returns404() throws Exception {
        when(coffeeBeanService.save(any(CoffeeBean.class))).thenThrow(CoffeeBeanNotFoundException.class);
        mvc.perform(put(API_BASE + "/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testBean1)))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(coffeeBeanService, times(1)).findCoffeeBeanById(anyInt());
        verify(coffeeBeanService, times(0)).save(any(CoffeeBean.class));
    }

    @Test
    public void deleteCoffeeBean_whenSuccessful_returns204() throws Exception {
        when(coffeeBeanService.findCoffeeBeanById(anyInt())).thenReturn(Optional.of(testBean1));
        doNothing().when(coffeeBeanService).deleteCoffeeBeanById(anyInt());
        mvc.perform(delete(API_BASE + "/{id}", 1))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(coffeeBeanService, times(1)).findCoffeeBeanById(anyInt());
        verify(coffeeBeanService, times(1)).deleteCoffeeBeanById(anyInt());
    }

    @Test
    public void deleteCoffeeBean_whenIdNotFound_returns404() throws Exception {
        when(coffeeBeanService.findCoffeeBeanById(anyInt())).thenReturn(Optional.empty());
        doNothing().when(coffeeBeanService).deleteCoffeeBeanById(anyInt());
        mvc.perform(delete(API_BASE + "/{id}", 1))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(coffeeBeanService, times(1)).findCoffeeBeanById(anyInt());
        verify(coffeeBeanService, times(0)).deleteCoffeeBeanById(anyInt());
    }
}