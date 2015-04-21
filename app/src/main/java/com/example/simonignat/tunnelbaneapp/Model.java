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
                    String siteURL = "http://api.sl.se/api2/typeahead.xml?key=f7a9ba6bee8a46f9a8d07629719d3935&searchstring=Tekniskahogskolan&stationsonly=true&maxresults=10";

                    siteAPIHandle = new URLRequest(siteURL);

                    siteXMLStream = siteAPIHandle.getResponseFromUrl();

                    Site test = xmlParser.getSiteInfo(new ByteArrayInputStream(siteXMLStream.getBytes(StandardCharsets.UTF_8)));

                    Log.w("test", test.toString());

                    setCurrentSite(xmlParser.getSiteInfo(new ByteArrayInputStream(siteXMLStream.getBytes(StandardCharsets.UTF_8))));

                    //HARDCODED FOR NOW
                    // String departureURL = "http://api.sl.se/api2/realtimedepartures.xml?key=985c280f5aab414d9584b8a58230a386&siteid=9192&timewindow=15"

                    Thread departureFetcher = new Thread(new Runnable() {

                        @Override
                        public void run(){
                            try{
                                //String departureURL = DepartureURLCreator.createURL(getCurrentSite());


                                String departureURL = "http://api.sl.se/api2/realtimedepartures.xml?key=985c280f5aab414d9584b8a58230a386&siteid=9204&timewindow=15";

                                departuresAPIHandle = new URLRequest(departureURL);

                                departuresXMLStream = departuresAPIHandle.getResponseFromUrl();


                                setTransports(xmlParser.getDepartures(new ByteArrayInputStream(departuresXMLStream.getBytes(StandardCharsets.UTF_8))));


                            }catch(Exception e){
                                Log.w("e2","E2");
                                e.printStackTrace();
                            }


                            Runnable runOnMainThrad = new Runnable(){
                                public void run(){
                                    busText.setText(nextBusToString());
                                }
                            };

                            Runnable runOnMainThrad2 = new Runnable(){

                                public void run(){
                                    metroText.setText(nextMetroToString());
                                }
                            };


                            //Updating view.

                            busText.post(runOnMainThrad);
                            metroText.post((runOnMainThrad2));
                        }});


                    departureFetcher.start();


                } catch (Exception e) {

                    Log.w("e1","E1");

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