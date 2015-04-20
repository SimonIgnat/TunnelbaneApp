package com.example.simonignat.tunnelbaneapp;

import android.os.Parcelable;
import android.util.Log;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static java.nio.charset.StandardCharsets.*;

/**
 * Created by simonignat on 2015-04-18.
 */



// Updated 20:15

    
public class Model {


    static ArrayList<Transport> transports = new ArrayList<Transport>();


    Site currentSite;

    URLRequest siteAPIHandle = null;
    URLRequest departuresAPIHandle = null;

    String outputString = "hej";

    String siteXMLStream;
    String departuresXMLStream;

    String userInput;

   XMLParser xmlParser = new XMLParser();

    // final String UI = null;
    private int timeStep = 50;



    Model(String uI) {
        //final String userInput = uI;
        //this.currentSite = new Site(9000, "Central Stationen", null);

        setUserInput(uI);
        initSite();

    }


    /**
     * <Fancy text></Fancy>
     *
     * @throws
     *
     * @param time The display time
     * @return Time integer
     */
    void initSite() {



        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                //String siteURL = SiteURLCreator.createURL(getUserInput());


                try {

                    //HARDCODED FOR NOW
                    String siteURL = "http://api.sl.se/api2/typeahead.xml?key=f7a9ba6bee8a46f9a8d07629719d3935&searchstring=slussen&stationsonly=true&maxresults=10";

                    siteAPIHandle = new URLRequest(siteURL);

                    siteXMLStream = siteAPIHandle.getResponseFromUrl();

                    Site test = xmlParser.getSiteInfo(new ByteArrayInputStream(siteXMLStream.getBytes(StandardCharsets.UTF_8)));


                    Log.w("test", test.toString());

                    setCurrentSite(xmlParser.getSiteInfo(new ByteArrayInputStream(siteXMLStream.getBytes(StandardCharsets.UTF_8))));


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();



        updateDepartures();
    }


    /**
     * <Fancy text></Fancy>
     *
     * @throws
     *
     * @param time The display time
     * @return Time integer
     */
    void updateDepartures() {


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                //String departureURL = DepartureURLCreator.createURL(getCurrentSite());

                try {

                    //HARDCODED FOR NOW
                    String departureURL = "http://api.sl.se/api2/realtimedepartures.xml?key=985c280f5aab414d9584b8a58230a386&siteid=9192&timewindow=15";

                    departuresAPIHandle = new URLRequest(departureURL);

                    departuresXMLStream = departuresAPIHandle.getResponseFromUrl();

                    setTransports(xmlParser.getDepartures(new ByteArrayInputStream(departuresXMLStream.getBytes(StandardCharsets.UTF_8))));


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


        thread.start();
    }



    long getTimeStep() {
        return timeStep;
    }

    void setTimeStep(int s) {
        timeStep = s;
    }


    /**
     * <Fancy text></Fancy>
     *
     * @throws
     *
     * @param time The display time
     * @return Time integer
     */
    String nextMetroToString() {

        for (Transport t : transports) {
            if (t.getClass().equals(Metro.class)) {
                String string = "Tunnelbana: " + t.getName() + "  -  "  + t.getTime();
                return string;
            }
        }
        return " ";
    }

    /**
     * <Fancy text></Fancy>
     *
     * @throws
     *
     * @param time The display time
     * @return Time integer
     */
    String nextBusToString() {

        for (Transport t : transports) {
            if (t.getClass().equals(Bus.class)) {
                return "Buss: " + t.getName() + "  -  "  + t.getTime();
            }
        }
        return " ";
    }

    /**
     * <Fancy text></Fancy>
     *
     * @throws
     *
     * @param time The display time
     * @return Time integer
     */
    String nextTrainToString() {

        for (Transport t : transports) {
            if (t.getClass().equals(Train.class)) {
                return "Pendeltåg: " + t.getName() + "  -  "  + t.getTime();
            }
        }
    return " ";
    }


    void setCurrentSite(Site site){
        currentSite = site;
    }

    Site getCurrentSite(){
        return currentSite;
    }


    void setTransports(ArrayList<Transport> transport){
        transports = transport;
    }

    void setUserInput(String userinput) {
        userInput = userinput;
    }

    String getUserInput(){
        return this.userInput;
    }
}



