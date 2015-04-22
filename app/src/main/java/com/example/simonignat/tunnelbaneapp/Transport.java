package com.example.simonignat.tunnelbaneapp;

import java.util.Calendar;

/**
 *
 */
public abstract class Transport {

    private String fancyDepartureTime;
    private String fancyLineName;
    private String lineExceptions;

    public String getLineExceptions() {
        return lineExceptions;
    }

    public void setLineExceptions(String lineExceptions) {
        this.lineExceptions = lineExceptions;
    }

    public Transport(String destination, String time){
        fancyDepartureTime=time;
        fancyLineName=destination;
    }

    public String getTime() {
        return fancyDepartureTime;
    }

    public void setDepartureTime(String time) {
        fancyDepartureTime = time;
    }

    public String getName() {
        return fancyLineName;
    }

    public void setLineName(String name) {
        fancyLineName = name;
    }

    /**
     * Converts display time string to
     * Integer.
     * @throws NumberFormatException
     *
     * @param time The display time
     * @return Time integer
     */
    public static int timeStringToInt(String time) throws NumberFormatException{
        int output=-1;
        if(time.matches(":")){
            String[] tokens = time.split(":");
            if(tokens.length!=2){
                throw new NumberFormatException();
            }else{
                Calendar cal = Calendar.getInstance();
               // tokens[0]-cal.getTime();
               // cal.getTime().getHours()
            }
        }

    //Nvm


        try {
            output=Integer.valueOf(time);
        } catch (NumberFormatException e) {
            if(time.matches("(?i)Nu")){
                output=0;
            }else{
                throw new NumberFormatException();
            }
        }
        return output;
    }
}
