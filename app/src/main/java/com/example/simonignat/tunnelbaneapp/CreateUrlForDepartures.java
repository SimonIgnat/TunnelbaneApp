package com.example.simonignat.tunnelbaneapp;

/**
 * Created by simonignat on 2015-04-18.
 *
 *
 *
 *
 *
 */


public class CreateUrlForDepartures{


    final String APIKey = "163f4d0ce1814a90b4d394367ee86601";
    final String parseFormat = "xml";
    final String timeSpan = "10";

    Site departureSite;

    CreateUrlForDepartures(Site site){

        this.departureSite = site;
    }


    public String createURL(){
        return "https://www.api.sl.se/api2/realtimedepartures." +
                parseFormat +
                "?key=" +
                APIKey +
                "&siteid=" +
                (String)this.departureSite.getId() +
                "&timewindow=" +
                timeSpan;

    }
}



