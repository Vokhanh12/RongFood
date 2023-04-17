package com.example.test.Model;

public class Location {
    private double latitude;
    private double longitude;

    public Location(double Latitude,double Longitude){
        this.latitude=Latitude;
        this.longitude=Longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLatitude(double latitude) {
        latitude = latitude;
    }

    public void setLongitude(double longitude) {
        longitude = longitude;
    }
}
