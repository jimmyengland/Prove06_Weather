package com.example.prove06_weather;

import com.google.gson.annotations.SerializedName;


import java.util.List;

/**
 * This is essentially an EntityBean that holds the information for a complete forecast result.
 */
public class WeatherForecast {

    @SerializedName("list")
    private List<WeatherForecastItem> forecastItems;

    public List<WeatherForecastItem> getForecastItems() {
        return forecastItems;
    }

    public void setForecastItems(List<WeatherForecastItem> forecastItems) {
        this.forecastItems = forecastItems;
    }
}