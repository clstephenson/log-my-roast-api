package com.clstephenson.logmyroast.exceptions;

public class CoffeeBeanNotFoundException extends RuntimeException {

    public CoffeeBeanNotFoundException(int id) {
        super("Could not find coffee bean " + id);
    }

}
