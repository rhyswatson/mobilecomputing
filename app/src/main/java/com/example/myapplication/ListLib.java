package com.example.myapplication;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class ListLib {

    private ListView listView;
    private ArrayList<String> arrayList;

    ListLib(ListView listView) {
        this.listView = listView;
        this.arrayList = new ArrayList<String>();
    }

    public ListView getListView() {
        return this.listView;
    }

    public void populateListItem(JSONObject cityObject, String city) {
        arrayList.add(city);
    }

    public void populateList(Context context) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, this.arrayList);
        listView.setAdapter(arrayAdapter);
    }

}
