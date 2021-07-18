package com.example.tripdivine.dto;

import com.google.android.gms.maps.model.LatLng;

public class LocationMasterDTO {

    private String markerTitle;
    private LatLng latLng;
    private Class aClass;

    public String getMarkerTitle() {
        return markerTitle;
    }

    public void setMarkerTitle(String markerTitle) {
        this.markerTitle = markerTitle;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public Class getaClass() {
        return aClass;
    }

    public void setaClass(Class aClass) {
        this.aClass = aClass;
    }

    public LocationMasterDTO() {
    }

    public LocationMasterDTO(String markerTitle, LatLng latLng, Class aClass) {
        this.markerTitle = markerTitle;
        this.latLng = latLng;
        this.aClass = aClass;
    }
}
