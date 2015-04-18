package com.example.simonignat.tunnelbaneapp;

import android.location.Location;

/**
 * Created by SU on 2015-04-18.
 */
public class Site {
    int siteId;
    String name;
    Location location;
    public Site(){
    }
    public int getId(){
        return siteId;
    }
    public String getName(){
        return name;
    }
    public Location getLocation(){
        return location;
    }
}
