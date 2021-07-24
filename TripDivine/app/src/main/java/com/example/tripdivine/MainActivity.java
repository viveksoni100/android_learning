package com.example.tripdivine;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private SignInButton signInButton;
    private GoogleApiClient googleApiClient;
    private SQLiteDatabase database;

    private static final int SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        askForLocationPermission();
        database = setUpDatabaseAndUserTable();

        if(database != null && isUserAlreadySignedIn(database)) {
            Intent intent = new Intent(this, NotificationDrawer.class);
            intent.putExtra("isUserSignedIn", true);
            startActivity(intent);
            Toast.makeText(this, "User is already signed in, bring me to Notification Drawer...!", Toast.LENGTH_SHORT).show();
        } else {
            setUpGoogleSignInOptions();
        }
    }

    private boolean isUserAlreadySignedIn(SQLiteDatabase database) {
        try {
            Cursor cursor = database.rawQuery("SELECT display_name FROM users", null);
            if (cursor != null) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private SQLiteDatabase setUpDatabaseAndUserTable() {
        try {
            SQLiteDatabase database = this.openOrCreateDatabase("trip_divine_db", MODE_PRIVATE, null);
            database.execSQL("CREATE TABLE IF NOT EXISTS users(id, VARCHAR, display_name VARCHAR, " +
                    "email VARCHAR, photo_url VARCHAR(500))");
            return database;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void askForLocationPermission() {
        if (Build.VERSION.SDK_INT < 23) {
            Toast.makeText(this, "Android Marshmallow or above is required", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            // ask user for granting location service so our app can pin point the GPS location of the device
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                Log.i("location permission : ", "granted...");
            }
        }
    }

    // process the users response (yes or no) to the permission granting request
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Log.i("location permission : ", "granted...");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGN_IN) {
            GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (googleSignInResult.isSuccess()) {
                Intent intent = new Intent(this, NotificationDrawer.class);
                intent.putExtra("isUserSignedIn", false);
                startActivity(intent);
                finish();   // if user presses back button, close the app.
            } else {
                Toast.makeText(this, "Login has failed!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setUpGoogleSignInOptions() {
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions).build();
        signInButton = findViewById(R.id.googleSignIn);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, SIGN_IN);
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
