package com.example.tamaramamedova.weatherapp;

/**
 * Exception if location has not been found
 */

public class LocationIsNotAvailable extends Exception {
    public LocationIsNotAvailable(String message){
        super(message);
    }
}
