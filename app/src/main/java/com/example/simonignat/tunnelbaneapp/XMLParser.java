package com.example.simonignat.tunnelbaneapp;

import android.location.Location;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class XMLParser {

    private String destination = "";
    private String lineNumber = "";
    private String time = "";
    private String tag = "";
    private ArrayList<Transport> transport;

    public XMLParser(){
    }
    public Site getSiteInfo(InputStream stream) throws XmlPullParserException, IOException {
        XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
        XmlPullParser parser = xmlFactoryObject.newPullParser();

        parser.setInput(stream, null);
        int event = parser.getEventType();
        String tag = "";
        while (event != XmlPullParser.END_DOCUMENT) {

            switch (event) {
                case XmlPullParser.START_TAG:
                    tag = parser.getName();
                    if(tag.equals("Site")) {
                        parseSiteData(parser);
                        //Log.w("parser")
                        Location loc =new Location(locationName);
                        loc.setLatitude(locationY);
                        loc.setLongitude(locationX);
                        return new Site(siteId, locationName,loc);
                    } break;
            }
            event = parser.next();
        }
        return null;
    }

    private int siteId;
    private String locationName;
    private int locationX;
    private int locationY;
    private void parseSiteData(XmlPullParser parser) throws XmlPullParserException,IOException{


        int event = parser.getEventType();
        boolean go=true;
        while (go) {
            String name = parser.getName();
            switch (event) {
                case XmlPullParser.START_TAG:
                    tag = parser.getName();
                    break;
                case XmlPullParser.TEXT:
                    if (tag.equals("SiteId"))
                        siteId = Integer.valueOf(parser.getText());

                    if (tag.equals("Name"))
                        locationName = parser.getText();

                    if (tag.equals("X")) {
                        locationX = Integer.valueOf(parser.getText());
                    }
                    if (tag.equals("Y")) {
                        locationY = Integer.valueOf(parser.getText());
                    }
                    break;

                case XmlPullParser.END_TAG:
                    tag="--";
                    if(parser.getName().equals("Site")){
                        go=false;
                    }
                    break;
            }
            if(go)
                event = parser.next();
        }
    }



    public ArrayList<Transport> getDepartures(InputStream stream) throws XmlPullParserException, IOException {
        transport =new ArrayList<Transport>();
        XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
        XmlPullParser parser = xmlFactoryObject.newPullParser();

        parser.setInput(stream, null);
        int event = parser.getEventType();
        String tag = "";
        while (event != XmlPullParser.END_DOCUMENT) {

            switch (event) {
                case XmlPullParser.START_TAG:
                    tag = parser.getName();
                    if(tag.equals("Metro"))
                        parseMetro(parser);
                    if(tag.equals("Bus"))
                        parseBuses(parser);
                    if(tag.equals("Train"))
                        parseTrain(parser);
                    if(tag.equals("Tram"))
                        parseTram(parser);
                    if(tag.equals("Boat"))
                        parseBoat(parser);
                    break;
            }
            event = parser.next();
        }


        return(transport);
    }

    private void parseLine(String type, XmlPullParser parser) throws XmlPullParserException,IOException {
        destination = "";
        lineNumber = "";
        time = "";
        tag = "";
        int event = parser.getEventType();
        boolean go=true;
        while (go) {
            String name = parser.getName();
            switch (event) {
                case XmlPullParser.START_TAG:
                    tag = parser.getName();
                    break;
                case XmlPullParser.TEXT:
                    if (tag.equals("Destination"))
                        destination = parser.getText();
                    if (tag.equals("DisplayTime"))
                        time = parser.getText();
                    if (tag.equals("LineNumber")) {
                        lineNumber = parser.getText();
                    }
                    break;

                case XmlPullParser.END_TAG:
                    tag="--";
                    if(parser.getName().equals(type)){
                        go=false;
                    }
                    break;
            }
            if(go)
                event = parser.next();
        }
    }
    private String stringSpaceAppender(String in, int len){
        String out=in;
        for(int i = 0;i<len-in.length();i++)
            out+=" ";
        return out;
    }
    private int lineSpaces=5;
    private int destSpaces=24;
    private String fancyNameCreator(String lineNumber, String destination) {
        return stringSpaceAppender(lineNumber,lineSpaces)+stringSpaceAppender(destination,destSpaces);
       // return String.format("%-16s%-50s",lineNumber,destination);
    }


    private void parseMetro(XmlPullParser parser) throws XmlPullParserException,IOException{
        parseLine("Metro",parser);
        transport.add(new Metro(fancyNameCreator(lineNumber,destination),time));
    }



    private void parseBuses(XmlPullParser parser) throws XmlPullParserException,IOException{
        parseLine("Bus",parser);
        transport.add(new Bus(fancyNameCreator(lineNumber,destination),time));
    }

    private void parseTrain(XmlPullParser parser) throws XmlPullParserException,IOException{
        parseLine("Train",parser);
        transport.add(new Train(fancyNameCreator(lineNumber,destination),time));
    }

    private void parseTram(XmlPullParser parser) throws XmlPullParserException,IOException{
        parseLine("Tram",parser);
        transport.add(new Tram(fancyNameCreator(lineNumber,destination),time));
    }

    private void parseBoat(XmlPullParser parser) throws XmlPullParserException,IOException{
        parseLine("Tram",parser);
        transport.add(new Boat(fancyNameCreator(lineNumber,destination),time));
    }
}
