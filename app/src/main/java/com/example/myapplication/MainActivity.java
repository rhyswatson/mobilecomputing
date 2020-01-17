package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    public static final String URL = "http://www.example.com/abc";
    private ArrayList<String> weatherList;

    /*
    excract contents from the JSON file for a days worth of weather info
    put it into the list
     */
    public void populateListItem(JSONObject daysWeather, String city) {
        try {
            weatherList.add(city + ": " + daysWeather.getString("weather") + " - " + daysWeather.getString("desc"));
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
        String url ="https://tranquil-stream-02820.herokuapp.com/api/cities";

        JsonObjectRequest objRequest = new JsonObjectRequest
            (Request.Method.GET, url, new Response.Listener<JSONObject>() {
                public void onResponse(JSONObject response) {
                    try {
                        JSONObject torontoWeather = response.getJSONObject("toronto");
                        Iterator<String> it = torontoWeather.keys();
                        while (it.hasNext()) {
                            String day = it.next();
                            populateListItem(torontoWeather.getJSONObject(day), "Toronto");
                        }
                        populateList(thisList);
                    } catch (JSONException e) {
                        System.err.println(e);
                    }
                }
            }, new Response.ErrorListener() {
                public void onErrorResponse(VolleyError error) {
                   System.err.println(error);
                }
            });

        // Add the request to the RequestQueue.
        queue.add(objRequest);
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
