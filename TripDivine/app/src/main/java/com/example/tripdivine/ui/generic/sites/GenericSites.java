package com.example.tripdivine.ui.generic.sites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.tripdivine.R;

public class GenericSites extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_sites);

        TextView titleTV = (TextView) findViewById(R.id.genericTitle);
        TextView notes = (TextView) findViewById(R.id.genericNote);

        Intent genericIntent = getIntent();
        titleTV.setText(genericIntent.getStringExtra("title_gu"));
        notes.setText(genericIntent.getStringExtra("notes_gu"));
    }
}
