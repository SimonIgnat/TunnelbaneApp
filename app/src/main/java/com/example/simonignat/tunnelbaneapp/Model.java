package com.example.simonignat.tunnelbaneapp;

/**
 * Created by simonignat on 2015-04-18.
 */
public class Model{

    String userInput;

    Transport[] transports;

    Site currentSite;

    URLRequest siteAPIHandle = null;
    URLRequest departuresAPIHandle = null;

    String outputString = "hej";

    String siteXMLStream;
    String departuresXMLStream;



    Model(String uI){
        this.userInput = uI;

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

            }
        });

        thread.start();
    }

    void updateDepartures(){


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                String departureURL = DepartureURLCreator.createURL(this.currentSite);

                try {

                    departuresAPIHandle = new URLRequest(departureURL);

                    departuresXMLStream = departuresAPIHandle.getResponseFromUrl();

                    this.transports = XMLParser.getDepartures(departuresXMLStream);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        thread.start();
    }

    public long getTimeStep() {
    }

    public Site getSite(){
        return site;

    }

}
