package com.example.tripdivine.ui.ahmedabad;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tripdivine.R;
import com.example.tripdivine.dto.LocationMasterGenericDTO;
import com.example.tripdivine.ui.generic.sites.GenericSites;
import com.example.tripdivine.util.LocationMasterService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(position)
                    /*.icon(BitmapDescriptorFactory.fromResource(R.drawable.tilak))*/
                    .title(locationMasterDTO.getTitle_gu()));
            marker.setTag(locationMasterDTO.getTitle_gu());
            marker.showInfoWindow();
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 15));
        }
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                Intent intent = new Intent(getContext(), GenericSites.class);
                intent.putExtra("title_gu", marker.getTitle());
                LocationMasterGenericDTO masterGenericDTO = null;
                try {
                    masterGenericDTO = LocationMasterService.getLocationMasterByTitle(masterGenericDTOS, marker.getTitle());
                } catch (Exception e) {
                    Log.e("ERROR : ", "Error while getting LocationMaster by Title");
                    Toast.makeText(getContext(), "Can't load " + marker.getTitle(), Toast.LENGTH_SHORT).show();
                }
                if (masterGenericDTO != null) {
                    intent.putExtra("notes_gu", masterGenericDTO.getNotes_gu());
                    intent.putExtra("image", masterGenericDTO.getImage());
                    startActivity(intent);
                }
                return false;
            }
        });
    }

}