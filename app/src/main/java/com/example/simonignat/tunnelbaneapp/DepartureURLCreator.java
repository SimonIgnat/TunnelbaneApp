package com.example.simonignat.tunnelbaneapp;

/**
 * Created by simonignat on 2015-04-18.
 *
 *
 *
 *
 *
 */


public class DepartureURLCreator {


    static final String APIKey = "163f4d0ce1814a90b4d394367ee86601";
    static final String parseFormat = "xml";
    static final String timeSpan = "10";

    static Site departureSite;

    DepartureURLCreator(){
    }


    public static String createURL(Site site){
        departureSite = site;

        return "https://www.api.sl.se/api2/realtimedepartures." +
                parseFormat +
                "?key=" +
                APIKey +
                "&siteid=" +
                (String)departureSite.getId() +
                "&timewindow=" +
                timeSpan;

    }
}



