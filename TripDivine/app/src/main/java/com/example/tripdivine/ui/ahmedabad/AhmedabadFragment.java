package com.example.tripdivine.ui.ahmedabad;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tripdivine.R;
import com.example.tripdivine.constants.Constant;
import com.example.tripdivine.ui.ahmedabad.sites.KalupurMandir;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class AhmedabadFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;

    ArrayList<LatLng> latLngArrayList = new ArrayList<LatLng>();

    LatLng ahmedabadMandir = new LatLng(23.030067150071346, 72.591600497919);
    LatLng teenDarwaza = new LatLng(23.024586964655857, 72.58457675559124);
    LatLng delhiDarwaza = new LatLng(23.03803056054976, 72.58799821326396);


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ahmedabad, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_ahmedabad);
        mapFragment.getMapAsync(this);

        latLngArrayList.add(ahmedabadMandir);
        latLngArrayList.add(teenDarwaza);
        latLngArrayList.add(delhiDarwaza);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //getAhmedabadRegion(googleMap);

        for (LatLng ll : latLngArrayList) {
            mMap.addMarker(new MarkerOptions().position(ll).title("Marker"));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ll, 15));
        }
    }

    private void getAhmedabadRegion(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng ahmedabadMandir = new LatLng(23.030067150071346, 72.591600497919);
        mMap.addMarker(new MarkerOptions().position(ahmedabadMandir).title("શ્રી સ્વામિનારાયણ મંદિર કાલુપુર"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ahmedabadMandir, 15));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent kalupurIntent = new Intent(getContext(), KalupurMandir.class);
                kalupurIntent.putExtra("title", "શ્રી સ્વામિનારાયણ મંદિર કાલુપુર");
                startActivity(kalupurIntent);
                return false;
            }
        });
    }
}