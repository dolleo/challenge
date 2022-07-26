package com.ldol.challenge.service.errors;

public class CityNotFoundException extends Exception {

    public CityNotFoundException(String cityGuid) {
        super("City with guid " + cityGuid + " was not found in database");
    }
}
