package com.example.simonignat.tunnelbaneapp;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;

/**
 * Created by simonignat on 2015-04-18.
 */
public class URLRequest extends AsyncTask{
        String[] URL = new String[2];


    /**
     * <Fancy text></Fancy>
     *
     * @throws
     *
     * @param time The display time
     * @return Time integer
     */
    URLRequest(String URL){
        this.URL[0] = URL;


    }


    /**
     * <Fancy text></Fancy>
     *
     * @throws
     *
     * @param time The display time
     * @return Time integer
     */
    @Override
    protected Object doInBackground(Object[] params){
          HttpClient httpclient = new DefaultHttpClient(); // Create HTTP Client


           HttpGet httpget = new HttpGet((String)params[0]); // Set the action you want to do
        HttpResponse response = null; // Executeit
        try {
            Log.w("Client started", "");
            response = httpclient.execute(httpget);
        } catch (IOException e1) {
            Log.w("Response Error", "error1");
            e1.printStackTrace();
        }
        assert response != null;
        HttpEntity entity = response.getEntity();
        InputStream is = null; // Create an InputStream with the response
        try {
            Log.w("inputStream started", "");
            is = entity.getContent();
        } catch (IOException e2) {
            Log.w("Entity Error", "error2");
            e2.printStackTrace();
        }
        BufferedReader reader = null;
        try {
            Log.w("BufferedReader started", "BUfferedReader");
            assert is != null;
            reader = new BufferedReader(new InputStreamReader(is, "utf-8"), 8);
        } catch (UnsupportedEncodingException e) {
            Log.w("BufferedReader Error", "error3");
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
            String line = null;


        try {
            assert reader != null;
            while ((line = reader.readLine()) != null)
                sb.append(line);
        } catch (IOException e4) {
            Log.w("Linereader error", "error4");
            e4.printStackTrace();
        }

        String resString = sb.toString();

        try {
            is.close();
        } catch (IOException e5) {
            Log.w("Close Error", "error5");
            e5.printStackTrace();

        }
        return  resString;
    }

    
    /**
     * <Fancy text></Fancy>
     *
     * @throws
     *
     * @param time The display time
     * @return Time integer
     */
    public String getResponseFromUrl() {
        return (String)(doInBackground(this.URL));
    }
}

