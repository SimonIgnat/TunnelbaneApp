package com.example.simonignat.tunnelbaneapp;

import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;

import android.widget.TextView;


import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements DataCollectorInterface {
    static Controller controller = new Controller();
    TextView infoText;
    DataCollector collector = new DataCollector();


    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        infoText = (TextView)findViewById(R.id.infoTextView);
        infoText.setTypeface(Typeface.MONOSPACE);
        collector.addListener(this);
        collector.startFetchData();



       }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public void onTransportDataCollected(ArrayList<Transport> transport) {

        String depMsg="";
        String metro="",bus="",tram="";


        for(Transport t:transport){
            if(t.getClass()==Metro.class){
                metro+=t.getName()+ depMsg+t.getTime()+"\n";
            }
            if(t.getClass()==Bus.class){
                bus+=t.getName()+ depMsg+t.getTime()+"\n";
            }
            if(t.getClass()==Tram.class){
                tram+=t.getName()+ depMsg+t.getTime()+"\n";
            }
        }
        infoText.setText("Tunnelbana:\n"+metro+"\nBussar:\n"+bus+"\nSpårvagn:\n"+tram);
    }
}
