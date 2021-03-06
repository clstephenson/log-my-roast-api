package com.clstephenson.logmyroast.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SourceNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(SourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String sourceNotFoundHandler(SourceNotFoundException ex) {
        return ex.getMessage();
    }
}
