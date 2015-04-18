package com.example.simonignat.tunnelbaneapp;

/**
 * Created by simonignat on 2015-04-18.
 */
public class CreateUrlForSite {

    final String APIKey = "f7a9ba6bee8a46f9a8d07629719d3935";
    final String parseFormat = "xml";
    final boolean stationsOnly = true;
    final String maxResults = "10";


    String userSearchString;

    CreateUrlForSite(String site){
        userSearchString = site;
    }

    String createURL(){
           return ("http://api.sl.se/api2/typeahead." +
                   parseFormat +
                   "?key=" +
                   APIKey  +
                   "&searchstring=" +
                   userSearchString  +
                   "&stationsonly=" +
                   stationsOnly +
                   "&maxresults=" +
                   maxResults);
    }
}





