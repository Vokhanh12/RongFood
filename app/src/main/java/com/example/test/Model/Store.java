package com.example.test.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.LinkedList;

public class Store implements Parcelable {
    private String _MaCH;
    private String _NguoiSoHu;
    private String _TenCH;

    private Location _Location;

    private LinkedList<VietnameseDelicacies> _llMenu;


    public Store(String MaCH, String NguoiSoHu, String TenCH, Location location, LinkedList<VietnameseDelicacies> llMenu) {
        this._MaCH = MaCH;
        this._NguoiSoHu = NguoiSoHu;
        this._TenCH = TenCH;
        this._Location = location;
        this._llMenu = llMenu;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_MaCH);
        dest.writeString(_NguoiSoHu);
        dest.writeString(_TenCH);
        dest.writeParcelable(_Location, flags);
        // Ghi các thuộc tính khác của Store vào dest
    }

    public static final Parcelable.Creator<Store> CREATOR = new Parcelable.Creator<Store>() {
        @Override
        public Store createFromParcel(Parcel source) {
            return new Store(source);
        }

        @Override
        public Store[] newArray(int size) {
            return new Store[size];
        }
    };

    private Store(Parcel source) {
        _MaCH = source.readString();
        _NguoiSoHu = source.readString();
        _TenCH = source.readString();
        _Location = source.readParcelable(Location.class.getClassLoader());
        // Đọc các thuộc tính khác của Store từ source
    }

    public LinkedList<VietnameseDelicacies> get_Menu() {
        return _llMenu;
    }

    public void set_Menu(LinkedList<VietnameseDelicacies> _Menu) {
        this._llMenu = _Menu;
    }

    public Location get_location() {
        return _Location;
    }

    public String get_MaCH() {
        return _MaCH;
    }

    public String get_NguoiSoHu() {
        return _NguoiSoHu;
    }

    public String getTenCH() {
        return _TenCH;
    }

    public void set_location(Location Location) {
        this._Location = _Location;
    }

    public void set_MaCH(String _MaCH) {
        this._MaCH = _MaCH;
    }

    public void set_NguoiSoHu(String NguoiSoHu) {
        this._NguoiSoHu = NguoiSoHu;
    }

    public void set_TenCH(String TenCH) {
        this._TenCH = TenCH;
    }


    public void set_Location(Location _Location) {
        this._Location = _Location;
    }

    public void set_llMenu(LinkedList<VietnameseDelicacies> _llMenu) {
        this._llMenu = _llMenu;
    }

    public String get_TenCH() {
        return _TenCH;
    }

    public Location get_Location() {
        return _Location;
    }

    public LinkedList<VietnameseDelicacies> get_llMenu() {
        return _llMenu;
    }
}


