package com.clstephenson.logmyroast.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DefaultRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultRestController.class);

    @GetMapping("/")
    public String home() {
        return "Hello World";
    }
}
