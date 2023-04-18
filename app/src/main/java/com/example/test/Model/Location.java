package com.example.test.Model;

public class Location {
    private double _latitude;
    private double _longitude;

    public Location(double Latitude,double Longitude){
        this._latitude=Latitude;
        this._longitude=Longitude;
    }

    public double getLatitude() {
        return _latitude;
    }

    public double getLongitude() {
        return _longitude;
    }

    public void setLatitude(double latitude) {
        _latitude = latitude;
    }

    public void setLongitude(double longitude) {
        _longitude = longitude;
    }
}
