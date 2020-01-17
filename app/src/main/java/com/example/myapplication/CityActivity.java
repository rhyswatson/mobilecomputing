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

public class CityActivity extends AppCompatActivity {

    public String city;
    ListLib lib;

    public void queryDay(String day) {
        Intent myIntent = new Intent(this, DayActivity.class);
        myIntent.putExtra("EXTRA_city", city); //Optional parameters
        myIntent.putExtra("EXTRA_day", day); //Optional parameters
        this.startActivity(myIntent);
    }

    public void performApiCall() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://mobile-computing-weather-app.herokuapp.com/api/cities/" + city;

        JsonObjectRequest objRequest = new JsonObjectRequest
                (Request.Method.GET, url, new Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject weatherDays = response.getJSONObject(city);
                            Iterator<String> it = weatherDays.keys();

                            while (it.hasNext()) {
                                String day = it.next();
                                lib.populateListItem(weatherDays.getJSONObject(day), day);
                            }
                            lib.populateList(getApplicationContext());
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
        setContentView(R.layout.activity_city);

        Intent intent = getIntent();
        city = intent.getStringExtra("EXTRA_city"); //if it's a string you stored.


        lib = new ListLib((ListView)findViewById(R.id.daysList));
        performApiCall();

        //setting an onclick event for our list items
        lib.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                queryDay(item);
            }
        });


    }
}
