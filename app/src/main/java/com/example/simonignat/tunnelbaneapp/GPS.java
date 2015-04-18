package com.example.simonignat.tunnelbaneapp;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;


/**
 * This class provides access to the GPS if
 * precent in the unit.
 *
 * @author Malte Gruber 2015
 */
public class GPS implements LocationListener{

    private long minTime=1000; //Minimum time between samples in milliseconds
    private boolean hasGps;
    private LocationManager locationManager;
    /**
     * Constructor will start the GPS.
     *
     * @param reference to Context of main activity.
     */
    public GPS(Context context){
        //Check if device has GPS
        PackageManager pm = context.getPackageManager();
        hasGps = pm.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS);
        //If device has GPS start GPS and set 'this' to listener
        if(hasGps) {
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTime, 0, this);
        }
    }
    /**
     * Releases the GPS
     */
    public void releaseGps(){
        locationManager.removeUpdates(this);//TODO full release?
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

}
