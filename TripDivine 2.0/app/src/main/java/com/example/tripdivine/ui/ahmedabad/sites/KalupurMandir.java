package com.example.tripdivine.ui.ahmedabad.sites;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tripdivine.R;

public class KalupurMandir extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalupur_mandir);

        TextView titleTV = (TextView) findViewById(R.id.delhiDarwazaTitle);

        Intent kalupurIntent = getIntent();
        titleTV.setText(kalupurIntent.getStringExtra("title"));

    }
}
