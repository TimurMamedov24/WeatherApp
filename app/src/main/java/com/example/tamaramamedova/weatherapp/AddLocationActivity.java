package com.example.tamaramamedova.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Database.Database;

/**
 * Main activity to get location from user input.
 * Uses activity_main2.xml
 */

public class AddLocationActivity extends AppCompatActivity {
    private Button b1;
    private EditText eText;
    private String cityName = null;
    private boolean connected;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove StrictMode in order to fetch Weather APi
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);



        setContentView(R.layout.activity_main);


        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            this.connected = true;
        }
        else
            this.connected = false;


        eText = findViewById(R.id.editText);
        b1 = findViewById(R.id.button);


        b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (eText.getText() == null) {
                        Toast.makeText(AddLocationActivity.this, "Please enter valid location data",
                                Toast.LENGTH_LONG).show();
                    }else {
                        cityName = eText.getText().toString();
                        if(connected) {

                            try {
                                String[] position = FetchData.getLatLong(AddLocationActivity.this, cityName);

                                createWeatherInfo(position);

                            } catch (LocationIsNotAvailable e) {
                                Toast.makeText(AddLocationActivity.this, e.getMessage(),
                                        Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }
                        }
                    }




                }

        });




    }



    private void createWeatherInfo(String[] position){

            Intent weatherInfo = new Intent(AddLocationActivity.this,WeatherINFO.class);
            weatherInfo.putExtra("Position",position);

            AddLocationActivity.this.startActivity(weatherInfo);



    }


}



