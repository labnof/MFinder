package com.example.babatundeanafi.mfinder.Model.control;

import android.content.Context;

import com.example.babatundeanafi.mfinder.Model.adapters.MySQLiteHelper;
import com.example.babatundeanafi.mfinder.Model.model.Mosque;
import com.example.babatundeanafi.mfinder.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * This class is called when we wish to pre-populate our Database with Mosques Objects
 * It use JSON-Java Deserialization: check if it still exist:
 * Reference : https://futurestud.io/tutorials/gson-getting-started-with-java-json-serialization-deserialization
 * Created by babatundeanafi on 06/11/2016.
 */

public class LoadDataFromJsonToDB {

    private Context context;



    public LoadDataFromJsonToDB(Context context) {
        this.context = context;
    }



    //reading Json fron raw and return an ArrayList of the mos it takes a raw resource e.g R.raw.mosques
    public String getMsqStringFromJson(int resource) {

        JSONResourceReader reader;
        reader = new JSONResourceReader(context.getResources(), resource);

        return reader.getJsonString();

    }

    //reading Json fron raw and return an ArrayList of the mos
    // JsonString = getMsqStringFromJson(R.raw.mosques) ;// string from JSON file
    public List<Mosque> loadFromJsonToDB(String JsonString ) {


        Gson gson = new Gson();  //Gson object
        Type mosqueListType = new TypeToken<ArrayList<Mosque>>() {}.getType();// gets the type of the list view
        List<Mosque> mosquesList = gson.fromJson(JsonString, mosqueListType);

        return mosquesList;

    }


    //Add mosques recoursively
    public void addMosquetoDBfromJSON(MySQLiteHelper database, List<Mosque> mos ){

        // Arraylist to hold list from JSON
        String JsonString = getMsqStringFromJson(R.raw.mosques) ;// string from JSON file
        mos = loadFromJsonToDB(JsonString);

        for (Mosque msq : mos) {
            database.addMosque(msq);
        }

    }



    }
