package com.example.simonignat.tunnelbaneapp;
import android.content.Context;
import android.location.Location;
import java.util.ArrayList;

/**
 * @description
 * This class provides functionality for measuring the distance between a
 * given point (default - Tekniska Högskolan, Stockholm - Sweden) and the location of the unit.
 * Don't forget to add the nececary permissions to the manifest!'
 * Place outside of application tags:
 * <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
 * <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
 * @author Malte Gruber.
 */
public class TekniskaGPS extends GPS {

    //The coordinates of the center point of the eastern metro entrances.
    //at Tekniska Högskolan
    private double tekniskaLat=59.34546187;
    private double tekniskaLong=18.07148838;

    private ArrayList<GpsDistanceChangedListener> listeners = new ArrayList<GpsDistanceChangedListener>();
    private Location tekniskaPos;

    /**
     * Constructor for distance measurement class.
     * The constructor will start the GPS unit.
     *
     * @param context Reference to Context of main activity.
     */
    public TekniskaGPS(Context context) {
        super(context);
        tekniskaPos = new Location("tekniska");
        tekniskaPos.setLongitude(tekniskaLong);
        tekniskaPos.setLatitude(tekniskaLat);
    }

    /**
     * Adds a listener that is called when the distance is updated
     *
     * @param listener Reference to listener
     *
     */
    public void addEventListener(GpsDistanceChangedListener listener){
        listeners.add(listener);
    }

    @Override
    public void onLocationChanged(Location location) {
        super.onLocationChanged(location);
        distanceChanged((int)location.distanceTo(tekniskaPos));
    }

    private void distanceChanged(int distance) {
        for(GpsDistanceChangedListener listener:listeners)
            if(listener!=null)
                listener.onDistanceChange(distance);
    }
}

