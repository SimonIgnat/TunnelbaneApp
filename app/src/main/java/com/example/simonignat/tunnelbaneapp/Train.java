package com.example.simonignat.tunnelbaneapp;


public class Train extends Transport {

    public Train(String destination, String time){
        super(destination ,time);
    }



    @Override
    public String getLineExceptions() {
        return super.getLineExceptions();
    }


    @Override
    public String getTime() {
        return super.getTime();
    }

    @Override
    public String getName() {
        return super.getName();
    }
}
