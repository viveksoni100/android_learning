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
import com.example.tripdivine.dto.LocationMasterDTO;
import com.example.tripdivine.dto.LocationMasterGenericDTO;
import com.example.tripdivine.ui.ahmedabad.sites.KalupurMandir;
import com.example.tripdivine.ui.generic.sites.GenericSites;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AhmedabadFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;

    ArrayList<LocationMasterGenericDTO> masterGenericDTOS = new ArrayList<>();

    public AhmedabadFragment() {
        retrieveDataFromDBAndFillItInList();
    }

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

    private void retrieveDataFromDBAndFillItInList() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Ahmedabad");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    LocationMasterGenericDTO masterGenericDTO = snapshot.getValue(LocationMasterGenericDTO.class);
                    masterGenericDTOS.add(masterGenericDTO);
                }
                onMapReady(mMap);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        for (LocationMasterGenericDTO locationMasterDTO : masterGenericDTOS) {
            LatLng position = new LatLng(locationMasterDTO.getLat(), locationMasterDTO.getLng());
            mMap.addMarker(new MarkerOptions()
                    .position(position)
                    .title(locationMasterDTO.getTitle_gu())).setTag(locationMasterDTO.getTitle_gu());
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 15));

            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {

                    Intent intent = new Intent(getContext(), GenericSites.class);
                    intent.putExtra("title", locationMasterDTO.getTitle_gu());
                    startActivity(intent);
                    return false;

                }
            });
        }
    }

}