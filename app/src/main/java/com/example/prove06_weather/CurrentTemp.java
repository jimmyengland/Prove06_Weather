package com.example.prove06_weather;

import android.app.Activity;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.util.Scanner;

public class CurrentTemp implements Runnable {
    private String city;
    private MainActivity activity;
    private static final String TAG = "currentTemp";

    public CurrentTemp (MainActivity activity, String city){
        this.activity = activity;
        this.city = city;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void run() {
        Log.i(TAG, "Getting Current Weather from " + city);
        WeatherDataLoader loader = new WeatherDataLoader();

        try {
            final WeatherConditions conditions = loader.getWeather(city);
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // This is the code that will run on the UI thread.
                    activity.handleWeatherResults(conditions);

                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}


