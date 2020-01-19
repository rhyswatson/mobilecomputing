package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

    ListLib lib;

    public void queryCity(String city) {
        Intent myIntent = new Intent(this, CityActivity.class);
        myIntent.putExtra("EXTRA_city", city); //Optional parameters
        this.startActivity(myIntent);
    }

    public void performApiCall() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://mobile-computing-weather-app.herokuapp.com/api/cities";

        JsonObjectRequest objRequest = new JsonObjectRequest
            (Request.Method.GET, url, new Response.Listener<JSONObject>() {
                public void onResponse(JSONObject response) {

                    Iterator<String> it = response.keys();
                    while (it.hasNext()) {
                        String city = it.next();
                        lib.populateListItem(city);
                    }
                    lib.populateList(getApplicationContext());
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

        lib = new ListLib((ListView)findViewById(R.id.list));
        performApiCall();

        //setting an onclick event for our list items
        lib.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                queryCity(item);
            }
        });
    }
}
