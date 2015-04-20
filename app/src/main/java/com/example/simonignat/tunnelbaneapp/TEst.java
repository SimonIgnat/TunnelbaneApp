package com.example.tunnelbanan;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



public class BajsActivity extends ActionBarActivity {
    Site currentSite = null;

    URLRequest siteAPIHandle = null;
    URLRequest apiHandle = null;
    String outputString = "hej";
    String siteXMLStream;
    String xmlStream;
    String userInput;
    XMLParser xmlParser = new XMLParser();
    private String SITE;
    private ArrayList<Transport> transports;
    Button b;
    EditText edit;
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit =(EditText)findViewById(R.id.editText1);
        text =(TextView)findViewById(R.id.textView1);
        b =(Button)findViewById(R.id.button1);
        b.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                b.setEnabled(false);
                b.setText("Loading...");
                SITE=edit.getText().toString();
                updateDepartures();
            }
        });
    }

    int Location=0;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    Site site;
    private Runnable runnable;
    int i=0;
    void updateDepartures(){

        Thread thread = new Thread(new Runnable() {
            @SuppressLint("NewApi")
            @Override
            public void run() {

                try {
                    //Get site
                    apiHandle = new URLRequest("http://api.sl.se/api2/typeahead.xml?key=7a2b5479580e47a4a3ddc444bc01f27e&searchstring="+SITE+"&stationsonly=true&maxresults=10");
                    xmlStream = apiHandle.getResponseFromUrl();
                    site=xmlParser.getSiteInfo(new ByteArrayInputStream(xmlStream.getBytes(StandardCharsets.UTF_8)));

                    //Get departures
                    apiHandle = new URLRequest("http://api.sl.se/api2/realtimedepartures.xml?key=985c280f5aab414d9584b8a58230a386&siteid="+site.getId()+"&timewindow=60");;
                    xmlStream = apiHandle.getResponseFromUrl();
                    setTransports(xmlParser.getDepartures(new ByteArrayInputStream(xmlStream.getBytes(StandardCharsets.UTF_8))));



                    runnable = new Runnable() {
                        private String[] types={"Metro","Bus","Trains","Trams","Boats"};
                        @Override
                        public void run() {
                            String out="";

                            for(String s:types){

                                out+=s+":\n";
                                for(Transport t:transports){
                                    if(t instanceof Metro && s.equals("Metro"))
                                        out+=": "+t.getName()+" -avgår om "+t.getTime()+"\n";
                                    if(t instanceof Bus&& s.equals("Bus"))
                                        out+=": "+t.getName()+" -avgår om "+t.getTime()+"\n";
                                    if(t instanceof Train&& s.equals("Trains"))
                                        out+=": "+t.getName()+" -avgår om "+t.getTime()+"\n";
                                    if(t instanceof Tram&& s.equals("Trams"))
                                        out+=": "+t.getName()+" -avgår om "+t.getTime()+"\n";
                                    if(t instanceof Boat&& s.equals("Boats"))
                                        out+=": "+t.getName()+" -avgår om "+t.getTime()+"\n";
                                }
                                out+="------\n";
                            }

                            text.setText(out);
                            edit.setText(site.getName());
                            b.setText("GET DEPARTURES");
                            b.setEnabled(true);
                        }
                    };
                    text.post(runnable);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
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
}