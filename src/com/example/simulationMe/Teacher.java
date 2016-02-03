package com.example.simulationMe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Tomer on 11/26/2015.
 */
public class Teacher extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher);

        Button start = (Button) findViewById(R.id.start);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    askForTimeCountDown("http://hakatonsync.azurewebsites.net/Webservice1.asmx/SetCountDownTime?lessonid=6");
                    Toast toast = Toast.makeText(getApplicationContext(),"Request to initiate filming has started",Toast.LENGTH_LONG);
                    toast.show();
                }
                catch (Exception e){
                    Log.i("error",e.getMessage());
                }
            }
        });
    }

    public static void askForTimeCountDown(String url) throws IOException {

        new RequestTask().execute(url);
    }

    }

