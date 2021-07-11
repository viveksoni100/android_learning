package com.example.jsonprocessing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String results = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStreamReader reader = new InputStreamReader(urlConnection.getInputStream());

                int data = reader.read();
                while (data != -1) {
                    char current = (char) data;
                    results += current;
                    data = reader.read();
                }

                return results;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                String resultFor = jsonObject.getString("results-for");
                JSONArray results = jsonObject.getJSONArray("results");

                for (int i=0; i < results.length(); i++) {
                    JSONObject object = results.getJSONObject(i);
                    Log.i("Object : ", object.toString());
                }
                //Log.i("Result For : ", resultFor);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute("https://www.superheroapi.com/api.php/4393973627314629/search/batman");
    }
}
