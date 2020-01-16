package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.BreakIterator;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String URL = "http://www.example.com/abc";
    private ArrayList<String> weatherList;

    /*
    excract contents from the JSON file for a days worth of weather info
    put it into the list
     */
    public void populateListItem(JSONObject daysWeather) {
        try {
            String weather = daysWeather.getString("weather");
            weatherList.add(weather);
            System.out.println(weather);
            String description = daysWeather.getString("desc");
            //weatherList.add(description);
        } catch (org.json.JSONException e) {
            System.out.println("JSONException");
        }

    }

    public void populateList(ListView thisList) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.weatherList);
        thisList.setAdapter(arrayAdapter);
    }

    public void performApiCall(final ListView thisList) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://192.168.0.14:1337/api/cities";

        //final TextView thisText = (TextView)findViewById(R.id.thisText);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //thisText.setText("Response is: "+ response.toString());

                        try {
                            //create json array, array of json objects
                            JSONArray allData = new JSONArray(response);

                            for ( int i = 0; i < allData.length() ; i++) {
                                //go through and get individual objects
                                JSONObject weeksWeather = allData.getJSONObject(i);
                                System.out.println("***************" + weeksWeather);

                                JSONObject torontoWeather = weeksWeather.getJSONObject("toronto");
                                JSONObject mondayToWeather = torontoWeather.getJSONObject("monday");
                                populateListItem(mondayToWeather);
                                System.out.println(mondayToWeather);


                                //for each object extract the required info
                            }
                            populateList(thisList);

                        } catch (org.json.JSONException e) {
                            System.out.println("JSONException");
                        }
                    }
                    //this sets up the actual list
                    //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, weatherList);
                    //thisList.setAdapter(arrayAdapter);

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("That didn't work!");
                System.out.println(error);
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView thisList = (ListView)findViewById(R.id.list);
        this.weatherList = new ArrayList<>();
        performApiCall(thisList);
        //System.out.println(weatherList);
        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.weatherList);

        //thisList.setAdapter(arrayAdapter);
        //setContentView(thisList);
    }
}
