package com.example.tripdivine.ui.ahmedabad.sites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.tripdivine.R;

public class TeenDarwaza extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teen_darwaza);

        TextView titleTV = (TextView) findViewById(R.id.delhiDarwazaTitle);

        Intent intent = getIntent();
        titleTV.setText(intent.getStringExtra("title"));
    }
}
