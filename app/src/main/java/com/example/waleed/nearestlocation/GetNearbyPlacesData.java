package com.example.waleed.nearestlocation;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.xml.sax.Parser;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

class GetNearbyPlacesData extends AsyncTask<Object,String,String> {
     private String googlePlacesData;
   private GoogleMap mMap;
    String url;


    @Override
    protected String doInBackground(Object... objects) {
        mMap=(GoogleMap)objects[0];
        url=(String)objects[1];
        DownloadUrl downloadURL =new DownloadUrl();
        try {
            googlePlacesData=downloadURL.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String s) {
    List<HashMap<String,String>>nearbyplaces;
    DataParser dataParser=new DataParser();
    nearbyplaces =dataParser.Parse(s);
        Log.d("nearbyplaces","called parse method");
        showNearbyplaces(nearbyplaces);

    }
    public void showNearbyplaces(List<HashMap<String,String>>nearbyplaces)//show all the places on the list
    {
        for (int i=0;i<nearbyplaces.size();i++)
        {
            MarkerOptions markerOptions=new MarkerOptions();
            HashMap<String,String>googleplace=nearbyplaces.get(i);
            String placename =googleplace.get("place_Name");
            String vicinity=googleplace.get("Vicinity");
            double lat=Double.parseDouble(googleplace.get("Lat"));
            double lng=Double.parseDouble(googleplace.get("long"));//Double because google place .get retrive string and we need lat double
            LatLng latLng=new LatLng(lat,lng);
            markerOptions.position(latLng);
            markerOptions.title(placename+":"+vicinity);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomBy(10));






        }



    }
}


