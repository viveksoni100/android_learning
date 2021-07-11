package com.example.downloadingwebcontent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;
            Log.i("URL : ", urls[0]);

            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStreamReader streamReader = new InputStreamReader(urlConnection.getInputStream());
                int data = streamReader.read();
                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = streamReader.read();
                }
                return result;
            } catch (Exception e) {
                e.printStackTrace();

                return "Error!!!";

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadTask downloadTask = new DownloadTask();
        String result = null;

        try {
            result = downloadTask.execute("https://viveksoni100.co.in/").get();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "A Locha Paida!!!", Toast.LENGTH_SHORT).show();
        }

        Log.i("RESULT : ", result);

    }
}
