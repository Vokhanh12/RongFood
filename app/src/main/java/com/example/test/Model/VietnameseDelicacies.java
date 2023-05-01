package com.example.test.Model;

public class VietnameseDelicacies {
    private String _KieuMonAn;
    private String _TenMon;
    private String _HinhAnh;
    private String _DiaPhuong;
    private String _MieuTa;

    private double _Price;

    public VietnameseDelicacies(String KieuMonAn, String TenMon, String HinhAnh, String DiaPhuong, String MieuTa,double Price) {
        this._KieuMonAn = KieuMonAn;
        this._TenMon = TenMon;
        this._HinhAnh = HinhAnh;
        this._DiaPhuong = DiaPhuong;
        this._MieuTa = MieuTa;
        this._Price = Price;
    }

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
