package com.example.myapplication;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class myLocationListener implements LocationListener {

    private Context activitycontext ;
    public myLocationListener (Context cont)
    {
        activitycontext = cont ;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        Toast.makeText(activitycontext , location.getLatitude()+", "+location.getLongitude() , Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        Toast.makeText(activitycontext , "Gps Enabled" , Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        Toast.makeText(activitycontext , "Gps Disabled" , Toast.LENGTH_LONG).show();
    }
}
