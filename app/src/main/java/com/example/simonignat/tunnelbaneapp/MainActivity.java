package com.example.simonignat.tunnelbaneapp;

import android.annotation.TargetApi;
import android.app.DownloadManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends ActionBarActivity {


    static Controller controller = new Controller();
    TextView tv;
    Model m;

    TextView busText;
    TextView metroText;
    TextView trainText;
    TextView siteTitle;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override

    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        busText = (TextView)findViewById(R.id.busView);
        metroText = (TextView)findViewById(R.id.metroView);
        trainText = (TextView)findViewById(R.id.trainView);

        siteTitle = (TextView)findViewById(R.id.siteTitle);

        busText.setText("");
        metroText.setText("");
        trainText.setText("");
        siteTitle.setText("");

        Button btn = (Button)findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userInput;

                userInput = ((EditText)findViewById(R.id.editText)).getText().toString();
                Model m = new Model(userInput);

                siteTitle.setText(userInput);

                busText.setText(m.nextBusToString());
                metroText.setText(m.nextMetroToString());
                trainText.setText(m.nextTrainToString());
            }
        });
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


}
