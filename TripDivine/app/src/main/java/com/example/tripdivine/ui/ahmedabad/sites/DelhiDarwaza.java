package com.example.tripdivine.ui.ahmedabad.sites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.tripdivine.R;

public class DelhiDarwaza extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delhi_darwaza);

        TextView titleTV = (TextView) findViewById(R.id.delhiDarwazaTitle);

        Intent kalupurIntent = getIntent();
        titleTV.setText(kalupurIntent.getStringExtra("title"));
    }
}
