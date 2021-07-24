package com.example.databasedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            SQLiteDatabase database = this.openOrCreateDatabase("demo_db", MODE_PRIVATE, null);
            database.execSQL("CREATE TABLE IF NOT EXISTS users(name VARCHAR, age INT(3))");
            database.execSQL("INSERT INTO users VALUES('Gordhanbhai', 42)");
            database.execSQL("INSERT INTO users VALUES('Shivlalseth', 40)");

            Cursor cursor = database.rawQuery("SELECT * FROM users", null);
            int nameIndex = cursor.getColumnIndex("name");
            int ageIndex = cursor.getColumnIndex("age");

            cursor.moveToFirst();

            while (cursor != null) {
                Log.i("name : ", cursor.getString(nameIndex));
                Log.i("age : ", cursor.getString(ageIndex));
                cursor.moveToNext();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
