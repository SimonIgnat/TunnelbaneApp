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


    static final String APIKey = "985c280f5aab414d9584b8a58230a386";
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
                departureSite.getId() +
                "&timewindow=" +
                timeSpan;

    }
}



