package com.example.logindemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public void login(View view) {
        EditText usernameET = (EditText) findViewById(R.id.usernameET);
        EditText passwordET = (EditText) findViewById(R.id.passwordET);
        String usr = usernameET.getText().toString();
        String pass = passwordET.getText().toString();
        Log.i("Info", "Username : " + usr);
        Log.i("Info", "Password : " + pass);
        Toast.makeText(this, "You entered : " + usr + " " + pass , Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
