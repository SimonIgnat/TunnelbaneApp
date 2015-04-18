package com.example.simonignat.tunnelbaneapp;
import android.util.Log;
import android.widget.TextView;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class XMLParser {

    public TextView tv;
    String destination = "";
    String lineNumber = "";
    String time = "";
    String tag = "";
    ArrayList<Transport> transport =new ArrayList<Transport>();

    public XMLParser(){
    }

    public ArrayList<Transport> parse(InputStream stream) throws XmlPullParserException, IOException {

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
    private void parseMetro(XmlPullParser parser) throws XmlPullParserException,IOException{
        parseLine("Metro",parser);
        transport.add(new Metro(destination +" "+ lineNumber,time));
    }
    private void parseBuses(XmlPullParser parser) throws XmlPullParserException,IOException{
        parseLine("Bus",parser);
        transport.add(new Bus(destination +" "+ lineNumber,time));
    }

    private void parseTrain(XmlPullParser parser) throws XmlPullParserException,IOException{
        parseLine("Train",parser);
        transport.add(new Train(destination +" "+ lineNumber,time));
    }

    private void parseTram(XmlPullParser parser) throws XmlPullParserException,IOException{
        parseLine("Tram",parser);
        transport.add(new Tram(destination +" "+ lineNumber,time));
    }

    private void parseBoat(XmlPullParser parser) throws XmlPullParserException,IOException{
        parseLine("Tram",parser);
        transport.add(new Boat(destination +" "+ lineNumber,time));
    }
}