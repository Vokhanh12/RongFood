package com.example.test.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Location implements Parcelable {
    private double _latitude;
    private double _longitude;

    public Location(double Latitude,double Longitude){
        this._latitude=Latitude;
        this._longitude=Longitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(_latitude);
        dest.writeDouble(_longitude);
    }

    public static final Parcelable.Creator<Location> CREATOR = new Parcelable.Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel source) {
            return new Location(source);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };

    private Location(Parcel source) {
        _latitude = source.readDouble();
        _longitude = source.readDouble();
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


    @Override
    public String toString(){
        return "Latitude:"+this._latitude+" Longitude:"+this._longitude;
    }
}
