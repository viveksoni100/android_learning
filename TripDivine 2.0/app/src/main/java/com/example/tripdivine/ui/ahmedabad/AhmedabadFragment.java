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
import com.example.tripdivine.ui.ahmedabad.sites.DelhiDarwaza;
import com.example.tripdivine.ui.ahmedabad.sites.KalupurMandir;
import com.example.tripdivine.ui.ahmedabad.sites.TeenDarwaza;
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

    ArrayList<LocationMasterDTO> masterDTOS = new ArrayList<LocationMasterDTO>();

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

        setUpMasterDTOS(masterDTOS);

    }

    private void setUpMasterDTOS(ArrayList<LocationMasterDTO> masterDTOS) {
        masterDTOS.add(addAhmedabadMandirDetail());
        masterDTOS.add(addTeenDarwazaDetail());
        masterDTOS.add(addDelhiDarwazaDetail());
    }

    private LocationMasterDTO addDelhiDarwazaDetail() {
        LocationMasterDTO delhiDarwazaLMDTO = new LocationMasterDTO();
        LatLng delhiDarwazaLatLng = new LatLng(23.03803056054976, 72.58799821326396);
        String delhiDarwazaMarkerTitle = Constant.SUB_LOCATIONS.delhiDarwaza.getValue();
        Class delhiDarwazaClass = DelhiDarwaza.class;
        delhiDarwazaLMDTO.setLatLng(delhiDarwazaLatLng);
        delhiDarwazaLMDTO.setMarkerTitle(delhiDarwazaMarkerTitle);
        delhiDarwazaLMDTO.setaClass(delhiDarwazaClass);

        return delhiDarwazaLMDTO;
    }

    private LocationMasterDTO addTeenDarwazaDetail() {
        LocationMasterDTO teenDarwazaLMDTO = new LocationMasterDTO();
        LatLng teenDarwazaLatLng = new LatLng(23.024586964655857, 72.58457675559124);
        String teenDarwazaMarkerTitle = Constant.SUB_LOCATIONS.teenDarwaza.getValue();
        Class teenDarwazaClass = TeenDarwaza.class;
        teenDarwazaLMDTO.setLatLng(teenDarwazaLatLng);
        teenDarwazaLMDTO.setMarkerTitle(teenDarwazaMarkerTitle);
        teenDarwazaLMDTO.setaClass(teenDarwazaClass);

        return teenDarwazaLMDTO;
    }

    private LocationMasterDTO addAhmedabadMandirDetail() {
        LocationMasterDTO ahmedabadMandirLMDTO = new LocationMasterDTO();
        LatLng ahmedabadMandirLatLng = new LatLng(23.030067150071346, 72.591600497919);
        String ahmedabadMandirMarkerTitle = Constant.BASE_LOCATIONS.kalupurMandir.getValue();
        Class ahmedabadMandirClass = KalupurMandir.class;
        ahmedabadMandirLMDTO.setLatLng(ahmedabadMandirLatLng);
        ahmedabadMandirLMDTO.setMarkerTitle(ahmedabadMandirMarkerTitle);
        ahmedabadMandirLMDTO.setaClass(ahmedabadMandirClass);

        return ahmedabadMandirLMDTO;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        for (LocationMasterDTO locationMasterDTO : masterDTOS) {
            mMap.addMarker(new MarkerOptions()
                    .position(locationMasterDTO.getLatLng())
                    .title(locationMasterDTO.getMarkerTitle())).setTag(locationMasterDTO.getMarkerTitle());
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(locationMasterDTO.getLatLng(), 15));

            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {

                    if (marker.getTag().toString().equals(Constant.BASE_LOCATIONS.kalupurMandir.getValue())) {
                        Intent intent = new Intent(getContext(), KalupurMandir.class);
                        intent.putExtra("title", Constant.BASE_LOCATIONS.kalupurMandir.getValue());
                        startActivity(intent);
                        return false;
                    } else if (marker.getTag().toString().equals(Constant.SUB_LOCATIONS.teenDarwaza.getValue())) {
                        Intent intent = new Intent(getContext(), TeenDarwaza.class);
                        intent.putExtra("title", Constant.SUB_LOCATIONS.teenDarwaza.getValue());
                        startActivity(intent);
                        return false;
                    } else if (marker.getTag().toString().equals(Constant.SUB_LOCATIONS.delhiDarwaza.getValue())) {
                            Intent intent = new Intent(getContext(), DelhiDarwaza.class);
                            intent.putExtra("title", Constant.SUB_LOCATIONS.delhiDarwaza.getValue());
                            startActivity(intent);
                            return false;
                    } else {
                        return false;
                    }
                }
            });
        }
    }

}