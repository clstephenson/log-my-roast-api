package com.clstephenson.logmyroast.exceptions;

public class RoastLogEntryNotFoundException extends RuntimeException {

    public RoastLogEntryNotFoundException(int id) {
        super("Could not find roast log entry " + id);
    }

}
