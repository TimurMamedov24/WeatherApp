package com.example.tamaramamedova.weatherapp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.pavlospt.CircleView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import Adapters.RecyclerViewAdapter;
import Adapters.Weather;

/**
 * Activity to show WeatherDATA.
 * Uses activity_main2.xml
 */

public class WeatherINFO extends AppCompatActivity {
    private boolean connection;
    private RecyclerView recyclerView;

    private RecyclerViewAdapter recyclerViewAdapter;

    private String[] position;

    private JSONObject forecast;

    private TextView currentDate;

    private ImageView weatherImage;

    private CircleView circleTitle;

    private TextView windResult;

    private TextView humidityResult;

    private String dayofWeek;

    private List<Weather> weekData;

    private String cityName = null;

    private String smallDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        TextView city = findViewById(R.id.city_country);
        this.currentDate = findViewById(R.id.current_date);
        this.weatherImage = findViewById(R.id.weather_icon);
        this.circleTitle = findViewById(R.id.weather_result);
        this.windResult = findViewById(R.id.wind_result);
        this.humidityResult = findViewById(R.id.humidity_result);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(WeatherINFO.this, 4);
        this.recyclerView = (RecyclerView) findViewById(R.id.week);
        this.recyclerView.setLayoutManager(gridLayoutManager);
        this.recyclerView.setHasFixedSize(true);
        //get current date
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE",Locale.getDefault());

        this.dayofWeek =date.getDate() + ", " + sdf.format(date);
        this.smallDay = sdf.format(date);
        ImageButton addLocation = (ImageButton) findViewById(R.id.add_location);
        addLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addLocationIntent = new Intent(WeatherINFO.this, AddLocationActivity.class);
                startActivity(addLocationIntent);
            }
        });

        try {
            //fetch data from Api and display info.
            this.position = getIntent().getStringArrayExtra("Position");
            JSONObject oneDayData = FetchData.getJSON(WeatherINFO.this, FinalValues.OPEN_WEATHER_MAP_API, position);
            this.forecast = FetchData.getJSON(WeatherINFO.this,FinalValues.OPEN_WEATHER_MAP_API_FORECAST,position);


            Weather todayWeather = getOneDayWeatherData(oneDayData);
            todayWeather.setDayOfWeek(this.dayofWeek);

            city.setText(cityName);


            currentDate.setText(todayWeather.getDayOfWeek());
            circleTitle.setTitleText(Integer.toString(todayWeather.getTemperature())+" C");
            circleTitle.setSubtitleText(todayWeather.getCondition());
            windResult.setText(todayWeather.getWind());
            humidityResult.setText(todayWeather.getHumidity());
            this.weekData = get5dayData(forecast);


            recyclerViewAdapter = new RecyclerViewAdapter(WeatherINFO.this, weekData);
            this.recyclerView.setAdapter(recyclerViewAdapter);

        } catch (LocationIsNotAvailable e){
            Toast.makeText(WeatherINFO.this, e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    /*
    Method to fetch weather from json object for one day.
     */

    private Weather getOneDayWeatherData(JSONObject weather){
          try{
              if(cityName == null){
                  this.cityName = weather.getString("name");
              }
              JSONObject cond = weather.getJSONArray("weather").getJSONObject(0);
              String condition = cond.getString("description");
              System.out.println(condition);
              String icon = cond.getString("icon");
              System.out.println(icon);
              JSONObject main = weather.getJSONObject("main");

              int temperature = (int) Double.parseDouble(main.getString("temp"));
              System.out.println(temperature);
              String humidity = main.getString("humidity")+ " %";
              System.out.println(humidity);

              JSONObject wind = weather.getJSONObject("wind");
              String winddata = wind.getString("speed") + " km/h";

              return new Weather(humidity,winddata,icon,temperature,condition);

          }catch (JSONException e){
              Toast.makeText(WeatherINFO.this,"JSON error",Toast.LENGTH_SHORT).show();
              e.printStackTrace();
          }
          return null;
    }
    /*
    Converting numerical date format to Week day.
    Returning Week day
     */

    private String convertTimeToDay(String time){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:SSSS", Locale.getDefault());
        String day = "";
        try {
            Date date = format.parse(time);
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE",Locale.getDefault());
            day = sdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day;
    }

    //Get weather for 5 days.
    private List<Weather> get5dayData(JSONObject weather){
        String currentday = smallDay;
        System.out.println(currentday);
        List<Weather> days = new ArrayList<>();
        try {
            JSONArray list = weather.getJSONArray("list");
            for(int i = 0;i < list.length();i++){
                JSONObject ob = (JSONObject) list.get(i);
                String date = convertTimeToDay(ob.getString("dt_txt"));
                if(!date.equals(currentday)){
                    currentday = date;
                    System.out.println(currentday);
                    Weather day = getOneDayWeatherData(ob);
                    day.setDayOfWeek(currentday);
                    days.add(day);
                }
            }
            return days;

        } catch (JSONException e){
            Toast.makeText(WeatherINFO.this,"Cannot get week data",Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return null;
        }


    }


}
