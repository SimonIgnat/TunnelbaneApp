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


    Boolean siteInitialized = false;

    Site currentSite;

    URLRequest siteAPIHandle = null;
    URLRequest departuresAPIHandle = null;

    TextView busText;
    TextView metroText;
    TextView titleText;

    String outputString = "hej";

    String siteXMLStream;
    String departuresXMLStream;

    String userInput;

   XMLParser xmlParser = new XMLParser();

    // final String UI = null;
    private int timeStep = 50;



    Model(String uI, TextView bV, TextView mV, TextView sT) {
        //final String userInput = uI;
        //this.currentSite = new Site(9000, "Central Stationen", null);

        this.busText = bV;
        this.metroText = mV;
        this.titleText = sT;
        setUserInput(uI);

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

                //String siteURL = SiteURLCreator.createURL(getUserInput());


                try {

                 //String siteURL = SiteURLCreator.createURL(getUserInput());
                 //HARDCODED FOR NOW
                    String siteURL = "http://api.sl.se/api2/typeahead.xml?key=f7a9ba6bee8a46f9a8d07629719d3935&searchstring=tekniskahogskolan&stationsonly=true&maxresults=10";

                    siteAPIHandle = new URLRequest(siteURL);

                    siteXMLStream = siteAPIHandle.getResponseFromUrl();

                    Site test = xmlParser.getSiteInfo(new ByteArrayInputStream(siteXMLStream.getBytes(StandardCharsets.UTF_8)));

                    Log.w("test", test.toString());

                    setCurrentSite(xmlParser.getSiteInfo(new ByteArrayInputStream(siteXMLStream.getBytes(StandardCharsets.UTF_8))));





                    String departureURL = DepartureURLCreator.createURL(getCurrentSite());

                    departuresAPIHandle = new URLRequest(departureURL);

                    departuresXMLStream = departuresAPIHandle.getResponseFromUrl();

                    setTransports(xmlParser.getDepartures(new ByteArrayInputStream(departuresXMLStream.getBytes(StandardCharsets.UTF_8))));


                } catch (Exception e) {
                    e.printStackTrace();
                }



                //MAGIC SAUCE:
                Runnable busTextThread = new Runnable() {
                    @Override
                    public void run() {
                        // This will run on main thread:
                        busText.setText(nextBusToString());
                    }
                };


                Runnable metroTextThread = new Runnable() {
                    @Override
                    public void run() {
                        // This will run on main thread:
                        metroText.setText(nextMetroToString());

                    }
                };


                Runnable titleTextThread = new Runnable() {
                    @Override
                    public void run() {
                        // This will run on main thread:
                        titleText.setText(currentSite.toString());
                    }
                };


                busText.post(busTextThread);
                metroText.post(metroTextThread);
                titleText.post(titleTextThread);
            }
        });

        thread.start();



        //updateDepartures();
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



