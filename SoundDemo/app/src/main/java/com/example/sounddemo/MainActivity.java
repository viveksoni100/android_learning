package com.example.sounddemo;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public void play(View view) {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.mystry_audio);
        mediaPlayer.start();
    }

    public void pause(View view) {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.mystry_audio);
        mediaPlayer.pause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
