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


public class Model {

    //Push Test malte!
    ArrayList<Transport> transports = new ArrayList<Transport>();


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


    TextView busText;
    TextView metroText;
    TextView siteTitle;

    Model(String uI, TextView busText,TextView metroText) {
        //final String userInput = uI;
        //this.currentSite = new Site(9000, "Central Stationen", null);
        this.busText=busText;
        this.metroText=metroText;
        setUserInput(uI);
        initSite();

    }

    void initSite() {

        /**
         * BEHÖVS TRÅDAR????
         *
         */

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
                //MAGIC SAUCE: 
                Runnable runOnMainThrad = new Runnable() {
                    @Override
                    public void run() {
                        // This will run on main thread:
                        busText.setText("LOL "+nextBusToString());
                    }

                };
                Runnable runOnMainThrad2 = new Runnable() {
                    @Override
                    public void run() {
                        // This will run on main thread:
                        metroText.setText("LOL "+nextMetroToString());
                    }
                };
                busText.post(runOnMainThrad);
                metroText.post(runOnMainThrad2);

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



    String nextMetroToString() {

        for (Transport t : transports) {
            if (t.getClass().equals(Metro.class)) {
                String string = "Tunnelbana: " + t.getName() + "  -  "  + t.getTime();
                return string;
            }
        }
        return " ";
    }


    String nextBusToString() {

        for (Transport t : transports) {
            if (t.getClass().equals(Bus.class)) {
                return "Buss: " + t.getName() + "  -  "  + t.getTime();
            }
        }
        return "";
    }


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
    }}