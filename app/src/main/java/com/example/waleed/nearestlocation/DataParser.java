package com.example.waleed.nearestlocation;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataParser {
    private HashMap<String,String>getplace(JSONObject googlePlaceJson)
    {
        HashMap<String,String>googlePlacesMap=new HashMap<>();
        String placeName="--NA--";
        String vicinity="--NA-";
        String Lattitude="";
        String Longtiude="";
        String refrence="";
        Log.d("DataParser","jsonobject ="+googlePlaceJson.toString());

        try {
        if (!googlePlaceJson.isNull("name"))
        {
            placeName=googlePlaceJson.getString("name");

                }
                if (!googlePlaceJson.isNull("vicinity"))
                {
                    vicinity=googlePlaceJson.getString("vicinity");
                }
                Lattitude=googlePlaceJson.getJSONObject("geomtry").getJSONObject("location").getString("lat");
                Longtiude=googlePlaceJson.getJSONObject("geomtry").getJSONObject("location").getString("long");
                refrence=googlePlaceJson.getString("refrence");
                googlePlacesMap.put("place_Name",placeName);
                googlePlacesMap.put("vicinity",vicinity);
                googlePlacesMap.put("lat",Lattitude);
                googlePlacesMap.put("long",Longtiude);
                googlePlacesMap.put("ref",refrence);




        } catch (JSONException e) {
            e.printStackTrace();
        }
        return googlePlacesMap;


    }
    private List <HashMap<String,String>>getplaces(JSONArray jsonArray)
    {
        int Count=jsonArray.length();
        List<HashMap<String,String>> placelist=new ArrayList<>();
        HashMap<String,String> placemap=null;
        for (int i=0;i<Count++;i++)
        {
            try {
                placemap=getplace((JSONObject) jsonArray.get(i));
                placelist.add(placemap);


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return placelist;


    }
    public List<HashMap<String,String>>Parse(String jsonData)
    {
        JSONArray jsonArray=null;
        JSONObject   jsonObject;

        try {
            jsonObject=new JSONObject(jsonData);
            jsonArray=jsonObject.getJSONArray("Result");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getplaces(jsonArray);


    }
}
