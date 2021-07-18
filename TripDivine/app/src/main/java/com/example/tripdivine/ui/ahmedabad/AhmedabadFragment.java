package com.example.tripdivine.ui.ahmedabad;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

public class AhmedabadFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;

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

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        getAhmedabadRegion(googleMap);
    }

    private void getAhmedabadRegion(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng vadtalMandir = new LatLng(23.030067150071346, 72.591600497919);
        mMap.addMarker(new MarkerOptions().position(vadtalMandir).title(Constant.BASE_LOCATIONS.kalupurMandir.getValue()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(vadtalMandir, 15));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent kalupurIntent = new Intent(getContext(), KalupurMandir.class);
                kalupurIntent.putExtra("title", Constant.BASE_LOCATIONS.kalupurMandir.getValue());
                startActivity(kalupurIntent);
                return false;
            }
        });
    }
}