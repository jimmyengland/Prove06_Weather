package com.example.prove06_weather;


import android.os.Build;
import androidx.annotation.RequiresApi;
import com.google.gson.Gson;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;


public class WeatherDataLoader {

    private static final String API_KEY_FILE = "aabf571ae3b6766a2927f44fdd37a467";
    private static final String URL_ENDPOINT_WEATHER = "https://api.openweathermap.org/data/2.5/weather";
    private static final String URL_ENDPOINT_FORECAST = "https://api.openweathermap.org/data/2.5/forecast";

    private String apiKey;
    private String apiCharset;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public WeatherDataLoader() {
        // The encoding methods below require an encoding, which for the web is UTF-8 by default.
        apiCharset = StandardCharsets.UTF_8.name();

        // NOTE: I have put the API Key in a separate file because I am posting this code publicly.
        // This way I can commit the rest of the code, but leave out my specific key.

        // If you were doing this for an internal project at a company, and the code would not be
        // posted publicly, it would be better to put the api key right in the code here

    }

    /**
     * This function does a generic web service GET HTTP request and returns the result.
     * @param url
     * @return
     * @throws IOException
     */
    private String getHttpResults(String url) throws IOException {
        // Make a connection to the provided URL
        URLConnection connection = new URL(url).openConnection();

        // Open the response stream and get a reader for it.
        InputStream responseStream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(responseStream));

        // If the API was written well, there will be only one line,
        // but just in case, I will go through each line in the response.

        // Because this could happen multiple times, a StringBuilder is much more efficient.
        StringBuilder stringBuilder = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }

        return stringBuilder.toString();
    }

    /**
     * This function will prepare the URL for the current Weather call
     * and return the JSON result.
     * @param city
     * @return
     * @throws IOException
     */
    private String getWeatherJson(String city) throws IOException {
        String url = String.format("%s?q=%s&apiKey=%s&units=imperial",
                URL_ENDPOINT_WEATHER,
                URLEncoder.encode(city, apiCharset),
                URLEncoder.encode(API_KEY_FILE, apiCharset));

        return getHttpResults(url);
    }

    /**
     * This function will prepare the URL for the forecast call
     * and return the JSON result.
     * @param city
     * @return
     * @throws IOException
     */
    private String getForecastJson(String city) throws IOException {
        String url = String.format("%s?q=%s&apiKey=%s&units=imperial",
                URL_ENDPOINT_FORECAST,
                URLEncoder.encode(city, apiCharset),
                URLEncoder.encode(API_KEY_FILE, apiCharset));

        return getHttpResults(url);
    }

    /**
     * Calls the weather api for the current weather of the provided city.
     * @param city
     * @return
     * @throws IOException
     */
    public WeatherConditions getWeather(String city) throws IOException {
        // Call the API
        String results = getWeatherJson(city);

        // Use GSON to deserialize the result
        Gson gson = new Gson();
        WeatherConditions conditions = gson.fromJson(results, WeatherConditions.class);

        return conditions;
    }

    /**
     * Calls the weather api for the forecast of the provided city.
     * @param city
     * @return
     * @throws IOException
     */
    public WeatherForecast getForecast(String city) throws IOException {
        // Call the API
        String results = getForecastJson(city);

        // Use GSON to deserialize the result
        Gson gson = new Gson();
        WeatherForecast forecast = gson.fromJson(results, WeatherForecast.class);

        return forecast;

    }
}
