package com.example.tripdivine.dto;

public class LocationMasterGenericDTO {

    private String image;
    private Double lat;
    private Double lng;
    private String notes_en;
    private String notes_gu;
    private String title_en;
    private String title_gu;
    private Class aClass;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getNotes_en() {
        return notes_en;
    }

    public void setNotes_en(String notes_en) {
        this.notes_en = notes_en;
    }

    public String getNotes_gu() {
        return notes_gu;
    }

    public void setNotes_gu(String notes_gu) {
        this.notes_gu = notes_gu;
    }

    public String getTitle_en() {
        return title_en;
    }

    public void setTitle_en(String title_en) {
        this.title_en = title_en;
    }

    public String getTitle_gu() {
        return title_gu;
    }

    public void setTitle_gu(String title_gu) {
        this.title_gu = title_gu;
    }

    public Class getaClass() {
        return aClass;
    }

    public void setaClass(Class aClass) {
        this.aClass = aClass;
    }

    @Override
    public String toString() {
        return "LocationMasterGenericDTO{" +
                "image='" + image + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", notes_en='" + notes_en + '\'' +
                ", notes_gu='" + notes_gu + '\'' +
                ", title_en='" + title_en + '\'' +
                ", title_gu='" + title_gu + '\'' +
                ", aClass=" + aClass +
                '}';
    }
}
