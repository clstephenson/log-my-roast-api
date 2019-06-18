package com.clstephenson.logmyroast.exceptions;

public class SourceNotFoundException extends RuntimeException {

    public SourceNotFoundException(int id) {
        super("Could not find source " + id);
    }

}
