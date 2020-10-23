package com.example.prove06_weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    //thread for current temp
    public void current(View view){
        EditText editText = (EditText) findViewById(R.id.location);
        String city = editText.getText().toString();
        CurrentTemp c = new CurrentTemp(this, city);
        Thread t1 = new Thread(c,"Current");
        t1.start();
    }

    //thread for forecast
    public void forecast(View view){
        EditText editText = (EditText) findViewById(R.id.location);
        String city = editText.getText().toString();
        Forecast f = new Forecast(this, city);
        Thread t2 = new Thread(f,"Current");
        t2.start();
    }

    //handles current temp
    public void handleWeatherResults(WeatherConditions output) {
        if (output == null) {
            // message saying doesn't work
            Log.d("MainActivity", "API results were null");

            Toast.makeText(this, "An error occurred when retrieving the weather",
                    Toast.LENGTH_LONG).show();
        } else {
            //logging the results
            Log.d("MainActivity", "Conditions: " + output.getMeasurements().toString());

            //getting the temp result
            Float temp = output.getMeasurements().get("temp");

            //creating toast to display temp
            Toast.makeText(this, "It is currently " + temp + " degrees.", Toast.LENGTH_LONG).show();
        }
    }

    //handles printing list into the listview
    public void handleForecastResults(List<String> output) {
        //sets up the array adapter
        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, (List<String>) output);

        //same as in current weather, says something didn't work if list is null
        if (output == null){
            Log.d("MainActivity", "API results were null");
            Toast.makeText(this, "An error occurred when retrieving the weather",
                    Toast.LENGTH_LONG).show();
        }
     else {
         //prints list into list view
            ListView listView = (ListView) findViewById(R.id.info);
            listView.setAdapter(itemsAdapter);
    }

    }


}