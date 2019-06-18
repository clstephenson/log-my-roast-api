package com.clstephenson.logmyroast.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(DefaultRestController.class)
public class DefaultRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void home_returns200AndHelloWorld() throws Exception {
        MvcResult result = mvc.perform(get("/api/")
                .contentType(MediaType.ALL))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString(), equalTo("Hello World"));
    }
}