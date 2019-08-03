package com.yogan.failsafe;

import android.os.AsyncTask;
import android.widget.Toast;
import common.logger.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DataSender extends AsyncTask<String, Void, Void> {
    @Override
    protected Void doInBackground(String... strings) {
        String idU = strings[0];
        HttpURLConnection initConn, startConn, stopConn, devConn = null;
        //Toast.makeText(this, "Hey! I got called", Toast.LENGTH_SHORT).show();


        try {
            URL initializeURL = new URL("http://172.20.10.6/digital/7/O");
            initConn = (HttpURLConnection) initializeURL.openConnection();
            initConn.setDoOutput(true);
            initConn.setRequestMethod("GET");
            initConn.setRequestProperty("Content-Type", "application/json");
            OutputStream os = initConn.getOutputStream();
            os.write("".getBytes());
            os.flush();
            Log.i("Test", "Executed");

            Log.i("Initconn", Integer.toString(initConn.getResponseCode()));
            initConn.disconnect();
            //Toast.makeText(this, "Initialize Connection Response:" + initConn.getResponseCode(), Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            e.printStackTrace();
        }


        //Toast.makeText(this, "Phase 2", Toast.LENGTH_SHORT).show();

        try {
            URL startURL = new URL("http://172.20.10.6/digital/7/1");
            startConn = (HttpURLConnection) startURL.openConnection();
            startConn.setDoOutput(true);
            startConn.setRequestMethod("GET");
            startConn.setRequestProperty("Content-Type", "application/json");
            OutputStream os = startConn.getOutputStream();
            os.write("".getBytes());
            os.flush();

            Log.i("Startconn", Integer.toString(startConn.getResponseCode()));
            startConn.disconnect();
            //Toast.makeText(this, "Start Light Connection Response:" + startConn.getResponseCode(), Toast.LENGTH_LONG).show();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                // TODO Auto-generated catch block
                ex.printStackTrace();
            }

            URL stopURL = new URL("http://172.20.10.6/digital/7/0");
            stopConn = (HttpURLConnection) stopURL.openConnection();
            stopConn.setDoOutput(true);
            stopConn.setRequestMethod("GET");
            stopConn.setRequestProperty("Content-Type", "application/json");
            OutputStream os2 = stopConn.getOutputStream();
            os2.write("".getBytes());
            os2.flush();


            Log.i("Stopconn", Integer.toString(stopConn.getResponseCode()));
            stopConn.disconnect();

            //Toast.makeText(this, "Stop light Connection Response:" + stopConn.getResponseCode(), Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            e.printStackTrace();
        }

        try
        {
            URL initializeURL = new URL("http://172.20.10.12:8080/Failsafe/rest/failsafe/authenticate/" + idU);
            devConn = (HttpURLConnection) initializeURL.openConnection();
            devConn.setDoOutput(true);
            devConn.setRequestMethod("POST");
            devConn.setRequestProperty("Content-Type", "application/json");
            OutputStream os = devConn.getOutputStream();
            os.write("".getBytes());
            os.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(devConn.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null)  {
                Log.i("inputLine", inputLine);
            }

            in.close();
            Log.i("devConn", Integer.toString(devConn.getResponseCode()));
            devConn.disconnect();
        }catch(Exception e)
        {
            e.printStackTrace();
        }






        //Toast.makeText(this, "Phase 3", Toast.LENGTH_SHORT).show();




        //Toast.makeText(this, "Done!", Toast.LENGTH_SHORT).show();
        return null;
    }
}
