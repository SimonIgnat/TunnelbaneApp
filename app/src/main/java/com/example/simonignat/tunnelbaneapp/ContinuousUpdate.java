package com.example.simonignat.tunnelbaneapp;

import android.util.Log;

import java.util.concurrent.TimeUnit;

/**
 * Created by simonignat on 2015-04-18.
 */


class ContinuousUpdate extends Thread{

    static Model model;


    ContinuousUpdate(Model m){
        model = m;

        startSimulation();

    }


    /**
     * <Fancy text></Fancy>
     *
     * @throws
     *
     * @param time The display time
     * @return Time integer
     */
    void startSimulation(){
        Thread controllerThread = new Thread(MainActivity.controller);

        controllerThread.start();

        continousDepartureUpdate();
    }


    /**
     * <Fancy text></Fancy>
     *
     * @throws
     *
     * @param time The display time
     * @return Time integer
     */
    void continousDepartureUpdate(){
        while(true){
            model.updateDepartures();

            try{
                TimeUnit.SECONDS.sleep(model.getTimeStep());

            }catch(InterruptedException e){
                Log.w("Awaken from sleep", "");
                e.printStackTrace();
            }
        }
    }
}
