package com.example.simonignat.tunnelbaneapp;

import android.util.Log;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Created by simonignat on 2015-04-18.
 */
public class Model{
    ArrayList<Transport> transports = new ArrayList<Transport>();

    Site currentSite = null;

    URLRequest siteAPIHandle = null;
    URLRequest departuresAPIHandle = null;

    String outputString = "hej";

    String siteXMLStream;
    String departuresXMLStream;

    String userInput;

    XMLParser xmlParser = new XMLParser();

   // final String UI = null;
    private int timeStep;


    Model(String uI){

        //final String userInput = uI;
        //this.currentSite = new Site(9000, "Central Stationen", null);

        setUserInput(uI);
        initSite();

    }

    void initSite(){

        /**
         * BEHÖVS TRÅDAR????
         *
         */

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String siteURL = SiteURLCreator.createURL("Stockholms Centralstation");


                try {

                    siteAPIHandle = new URLRequest(siteURL);

                    siteXMLStream = siteAPIHandle.getResponseFromUrl();

                    setCurrentSite(xmlParser.getSiteInfo(new ByteArrayInputStream(siteXMLStream.getBytes(StandardCharsets.UTF_8))));

                }catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    void updateDepartures(){


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                String departureURL = DepartureURLCreator.createURL(getCurrentSite());

                try {

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

    public long getTimeStep() {
        return timeStep;
    }

    public void setTimeStep(int s){
        timeStep = s;
    }

    public Site getSite(){
        return currentSite;

    }

    public String nextBusToString(){
        Bus b = null;

        for(Transport t: transports){
            try {
                b = (Bus) t;
            }catch(ClassCastException e){
                e.printStackTrace();
            }
        }
        String string = b.getName() + b.getTime();
        return string;
    }

    public String nextMetroToString(){
        Metro m = null;

        for(Transport t: transports){
            try {
                m = (Metro) t;
            }catch(ClassCastException e){
                e.printStackTrace();
            }
        }
        String string = m.getName() + m.getTime();
        return string;
    }

    public String nextTrainToString(){
        Metro tr = null;

        for(Transport t: transports) {
            try {
                tr = (Metro) t;
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        }

        String string = tr.getName() + tr.getTime();



        return string;
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

    public void setUserInput(String userinput) {
        this.userInput = userinput;
    }

    public String getUserInput(){
        return this.userInput;
    }
}
