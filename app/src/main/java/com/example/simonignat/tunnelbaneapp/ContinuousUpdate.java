package com.example.simonignat.tunnelbaneapp;

import android.util.Log;

/**
 * Created by simonignat on 2015-04-18.
 */


class ContinuousUpdate extends Thread{

    static Model model;


    ContinuousUpdate(Model m){
        model = m;

        startSimulation();

    }


    void startSimulation(){
        Thread controllerThread = new Thread(MainActivity.controller);

        controllerThread.start();

        continousDepartureUpdate();
    }

    void continousDepartureUpdate(){
        while(true){
            model.updateDepartures();

            try{
                sleep(model.getTimeStep());

            }catch(InterruptedException e){
                Log.w("Awaken from sleep", "");
                e.printStackTrace();
            }
        }
    }
}
