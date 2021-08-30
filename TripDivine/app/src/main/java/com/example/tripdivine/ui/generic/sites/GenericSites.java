package com.example.tripdivine.ui.generic.sites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tripdivine.R;
import com.squareup.picasso.Picasso;

public class GenericSites extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_sites);

        TextView titleTV = (TextView) findViewById(R.id.genericTitle);
        TextView notes = (TextView) findViewById(R.id.genericNote);
        ImageView img = (ImageView) findViewById(R.id.genericImage);

        Intent genericIntent = getIntent();
        titleTV.setText(genericIntent.getStringExtra("title_gu"));
        notes.setText(genericIntent.getStringExtra("notes_gu"));
        String imagePath = genericIntent.getStringExtra("image");
        //Picasso.get().load(imagePath).placeholder(R.mipmap.ic_launcher).into(img);
        Picasso.get().load(imagePath).placeholder(R.drawable.img_loader).into(img);
    }
}
