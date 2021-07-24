package com.example.tripdivine;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

public class NotificationDrawer extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private NavigationView navigationView;

    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions googleSignInOptions;

    View headView = navigationView.getHeaderView(0);

    SQLiteDatabase database = this.openOrCreateDatabase("trip_divine_db", MODE_PRIVATE, null);

    private boolean isUserSignedIn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_drawer);

        Intent intent = getIntent();
        isUserSignedIn = intent.getBooleanExtra("isUserSignedIn", true);

        if (!isUserSignedIn) {
            OAuth2SignInProcess();
        }
        InitNavigationDrawer();
    }

    private void InitNavigationDrawer() {

        if(isUserSignedIn) {
            setupNavigationHeadView(headView, database);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_location, R.id.nav_ahmedabad, R.id.nav_vadtal,
                R.id.nav_bhuj, R.id.nav_feedback, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    private void OAuth2SignInProcess() {
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notification_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logoutBTN:
                oauth2Logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void oauth2Logout() {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()) {
                    goToMainActivity();
                } else {
                    Toast.makeText(getApplicationContext(), "Logout is unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if (opr.isDone()) {
            GoogleSignInResult result = opr.get();
            handleSignInResults(result);
        } else {
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult result) {
                    handleSignInResults(result);
                }
            });
        }
    }

    private void handleSignInResults(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            try {
                if(!isUserSignedIn) {
                    if (database.rawQuery("SELECT * FROM users LIMIT 1", null) == null) {
                        database.execSQL("INSERT INTO users(id, display_name, email, photo_url) VALUES('" +account.getId() + "',"
                                + "'" + account.getDisplayName() + "',"
                                + "'" + account.getEmail() + "',"
                                + "'" + account.getPhotoUrl() + "'"
                        );
                    }

                    setupNavigationHeadView(headView, database);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            goToMainActivity();
        }
    }

    private void setupNavigationHeadView(View headView, SQLiteDatabase database) {
        Cursor cursor = database.rawQuery("SELECT * FROM users LIMIT 1", null);
        int displayNameIndex = cursor.getColumnIndex("display_name");
        int emailIndex = cursor.getColumnIndex("email");
        int photoUrlIndex = cursor.getColumnIndex("photo_url");

        cursor.moveToFirst();

        while (cursor != null) {
            TextView displayNameTV = headView.findViewById(R.id.displayName);
            displayNameTV.setText(cursor.getString(displayNameIndex));

            TextView emailAddress = headView.findViewById(R.id.emailAddress);
            emailAddress.setText(emailIndex);

            ImageView imageView = headView.findViewById(R.id.imageView);
            Picasso.get().load(photoUrlIndex).placeholder(R.mipmap.ic_launcher).into(imageView);
            cursor.moveToNext();
        }
    }

    private void goToMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
