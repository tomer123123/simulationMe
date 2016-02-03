package com.example.simulationMe;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import org.apache.commons.net.ftp.*;

import org.apache.commons.net.ftp.FTPClient;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Tomer on 11/26/2015.
 */
public class Student extends Activity {
    public static String filePath;
    Camera mCamera;
    static String serverResponse;
    static View viv;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student);

        Button imHere = (Button) findViewById(R.id.imHere);
        ;

        imHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viv = v;
                try {
                    askForTimeCountDown("http://hakatonsync.azurewebsites.net/Webservice1.asmx/GetCountDownTime?lessonid=6");
                } catch (Exception e) {
                    Log.i("error", e.getMessage());
                }
            }
        });
    }

    public void askForTimeCountDown(String url) throws IOException {
        new RequestTask1().execute(url);
        Toast toast = Toast.makeText(getApplicationContext(), "Waiting for filming to begin", Toast.LENGTH_LONG);
        toast.show();
    }

    private boolean safeCameraOpen(int id) {
        boolean qOpened = false;

        try {
            releaseCameraAndPreview();
            mCamera = Camera.open(id);
            qOpened = (mCamera != null);
        } catch (Exception e) {
            Log.e(getString(R.string.app_name), "failed to open Camera");
            e.printStackTrace();
        }

        return qOpened;
    }

    private void releaseCameraAndPreview() {
        //mPreview.setCamera(null);
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }

    private class RequestTask1 extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... uri) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response;
            String responseString = null;
            try {
                response = httpclient.execute(new HttpGet(uri[0]));
                StatusLine statusLine = response.getStatusLine();
                if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    response.getEntity().writeTo(out);
                    responseString = out.toString();
                    out.close();
                } else {
                    //Closes the connection.
                    response.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                }
            } catch (ClientProtocolException e) {
                //TODO Handle problems..
            } catch (IOException e) {
                //TODO Handle problems..
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            String subStr;
            int timeToWait;
            if (result != null) {
                subStr = result.substring(result.indexOf('#') + 1, result.length() - 9);
                timeToWait = Integer.parseInt(subStr);
            } else {
                timeToWait = 0;
            }

            Intent intent = new Intent(Student.this, cameraTest.class);

            intent.putExtra("Extra Data", timeToWait);

            startActivityForResult(intent, 1);
            intent.getData();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        File file = new File(filePath);
        String encoded;
        FileInputStream fileInputStream = null;
        byte[] bytes = new byte[(int) file.length()];
        try {
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytes);
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        encoded = Base64.encodeToString(bytes, 0);
        new RequestTask2().execute(encoded);


    }//onActivityResult


    private class RequestTask2 extends AsyncTask<String,Boolean , Boolean> {

        @Override
        protected Boolean doInBackground(String... encoded) {

            FTPClient con = null;

            try {
                con = new FTPClient();
                con.connect("23.97.148.106");//ftp://waws-prod-am2-047.ftp.azurewebsites.windows.net/");

                if (con.login("hakatonSync", "DlsyA5j3KgZXlaC0ltzJBug1dwAWhgEc5cwD80H1Skp4FznaNShjS9d2MDiT")) {
                    con.enterLocalPassiveMode(); // important!
                   // con.setFileType(FTP.BINARY_FILE_TYPE);
                    con.setFileType(org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE);

                    FileInputStream in = new FileInputStream(new File(filePath));
                    boolean result = con.storeFile("/"+System.currentTimeMillis()+".mp4", in);
                    in.close();
                    if (result) Log.v("upload result", "succeeded");
                    con.logout();
                    con.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }


        @Override
        protected void onPostExecute(Boolean params) {
            super.onPostExecute(params);
            Toast toast = Toast.makeText(getApplicationContext(), "File has been uplaoded", Toast.LENGTH_LONG);
            toast.show();
        }
    }

}

