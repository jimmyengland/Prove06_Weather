package com.example.prove06_weather;

import android.app.Activity;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Forecast implements Runnable {
    private String city;
    private MainActivity activity;
    private static final String TAG = "forecast";
    List<String> list = new ArrayList<String>();


    public Forecast (MainActivity activity, String city){
        this.activity = activity;
        this.city = city;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void run() {
        Log.d(TAG, "Getting Forecast from " + city);
        WeatherDataLoader loader = new WeatherDataLoader();
        try {
            WeatherForecast forecast = loader.getForecast(city);

        for (WeatherForecastItem item : forecast.getForecastItems()) {
            String time;
            String temp;

            //adding time to list
            list.add(time = item.getDateText());
            //adding temp to list
            list.add(temp = item.getMeasurements().get("temp").toString());
            //adding condition to list
            String conditions = "";
            if (item.getDescriptions().size() > 0) {
                list.add(conditions = item.getDescriptions().get(0).getDescription());
            }
//            add wind to list
//            list.add(wind = item.getWind().get("speed").toString());

            //showing results in log
            Log.i(TAG,"Time: " + time + " Temp: " + temp + " Conditions: " + conditions);

        }
        } catch (IOException e) {
            e.printStackTrace();
        }
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // This is the code that will run on the UI thread.
                activity.handleForecastResults(list);
            }
        });


    }


}


