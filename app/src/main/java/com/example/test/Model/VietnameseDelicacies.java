package com.example.test.Model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

public class VietnameseDelicacies  implements Parcelable {
    private String _KieuMonAn;
    private String _TenMon;
    private String _HinhAnh;
    private String _DiaPhuong;
    private String _MieuTa;

    private double _Price;

    //Dùng để chèn SeachView Store
    public VietnameseDelicacies(String KieuMonAn, String TenMon, String HinhAnh, String DiaPhuong, String MieuTa,double Price) {
        this._KieuMonAn = KieuMonAn;
        this._TenMon = TenMon;
        this._HinhAnh = HinhAnh;
        this._DiaPhuong = DiaPhuong;
        this._MieuTa = MieuTa;
        this._Price = Price;
    }

    //Dùng để chèn SeachView ảo
    public VietnameseDelicacies(String KieuMonAn, String TenMon, String HinhAnh, String DiaPhuong, String MieuTa){
        this._KieuMonAn = KieuMonAn;
        this._TenMon = TenMon;
        this._HinhAnh = HinhAnh;
        this._DiaPhuong = DiaPhuong;
        this._MieuTa = MieuTa;
    }


    protected VietnameseDelicacies(Parcel in) {
        _KieuMonAn = in.readString();
        _TenMon = in.readString();
        _HinhAnh = in.readString();
        _DiaPhuong = in.readString();
        _MieuTa = in.readString();
        _Price = in.readDouble();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {

        dest.writeString(_KieuMonAn);
        dest.writeString(_TenMon);
        dest.writeString(_HinhAnh);
        dest.writeString(_DiaPhuong);
        dest.writeString(_MieuTa);

    }

    public static final Parcelable.Creator<VietnameseDelicacies> CREATOR = new Creator<VietnameseDelicacies>() {
        @Override
        public VietnameseDelicacies createFromParcel(Parcel in) {
            return new VietnameseDelicacies(in);
        }

        @Override
        public VietnameseDelicacies[] newArray(int size) {
            return new VietnameseDelicacies[size];
        }
    };


    public String get_KieuMonAn() {
        return _KieuMonAn;
    }

    public String get_TenMon() {
        return _TenMon;
    }

    public String get_HinhAnh() {
        return _HinhAnh;
    }

    public String get_DiaPhuong() {
        return _DiaPhuong;
    }

    public String get_MieuTa() {
        return _MieuTa;
    }

    public double get_Price() {return _Price;}

    public void set_KieuMonAn(String _KieuMonAn) {
        this._KieuMonAn = _KieuMonAn;
    }

    public void set_TenMon(String _TenMon) {
        this._TenMon = _TenMon;
    }

    public void set_HinhAnh(String _HinhAnh) {
        this._HinhAnh = _HinhAnh;
    }

    public void set_DiaPhuong(String _DiaPhuong) {
        this._DiaPhuong = _DiaPhuong;
    }

    public void set_MieuTa(String _MieuTa) {
        this._MieuTa = _MieuTa;
    }

    public void set_Price(double _Price) {this._Price = _Price;}


}
