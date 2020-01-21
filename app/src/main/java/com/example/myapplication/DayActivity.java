package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class DayActivity extends AppCompatActivity {

    public String city;
    public String day;
    ListLib lib;

    public void performApiCall() {
        // Instantiate the RequestQueue.
        String url ="https://mobile-computing-weather-app.herokuapp.com/api/cities/" + city + "/" + day;

        lib.populateListItem("weather" + " : " + "desc");
        lib.populateList(getApplicationContext());

        // Add the request to the RequestQueue.

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

        Intent intent = getIntent();
        city = intent.getStringExtra("EXTRA_city"); //if it's a string you stored.
        day = intent.getStringExtra("EXTRA_day");

        lib = new ListLib((ListView)findViewById(R.id.weatherList));
        performApiCall();

    }
}
