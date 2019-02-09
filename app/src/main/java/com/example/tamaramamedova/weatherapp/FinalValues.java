package com.example.tamaramamedova.weatherapp;

/**
 * Holder for final values.
 */

public class FinalValues {
    //Values for API connection
    private static final String APPID = "3dc7262d11aa5ae80f7a0103cccd6b5b";
    public static final String OPEN_WEATHER_MAP_API =
            "http://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&units=metric&APPID="+APPID;
    public static final String OPEN_WEATHER_MAP_API_FORECAST =
            "http://api.openweathermap.org/data/2.5/forecast?lat=%s&lon=%s&units=metric&APPID="+APPID;
    public static final String GET_ICON_IMAGE = "http://openweathermap.org/img/w/%s.png";

    //Values for database
    public static final String DATABASE_NAME = "weatherData";
    public static final int DATABASE_VERSION = 1;
    public static final String CITYID = "LocationID";
    public static final String TABLE_NAME = "Weather";
    public static final String DATA = "WeatherData";
}
