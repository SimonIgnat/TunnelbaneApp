package com.example.simonignat.tunnelbaneapp;

import android.location.Location;

/**
 * Created by SU on 2015-04-18.
 */
public class Site {
    int siteId;
    String name;
    Location location;
    public Site(int siteId,String name,Location location){
        this.siteId=siteId;
        this.name=name;
        this.location=location;
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
