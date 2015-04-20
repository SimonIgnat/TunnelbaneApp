package com.example.simonignat.tunnelbaneapp;

import android.util.Log;

/**
 * Created by simonignat on 2015-04-18.
 */
public class SiteURLCreator {

    static final String APIKey = "f7a9ba6bee8a46f9a8d07629719d3935";
    static final String parseFormat = "xml";
    static final boolean stationsOnly = true;
    static final String maxResults = "10";


    static String userSearchString;

    SiteURLCreator(){
    }


    /**
     * <Fancy text></Fancy>
     *
     * @throws
     *
     * @param time The display time
     * @return Time integer
     */
    static String createURL(String site){
            userSearchString = site;

            return ("api.sl.se/api2/typeahead." +
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





