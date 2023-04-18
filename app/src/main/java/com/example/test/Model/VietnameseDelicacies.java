package com.example.test.Model;

public class VietnameseDelicacies {
    private String _KieuMonAn;
    private String _TenMon;
    private String _HinhAnh;
    private String _DiaPhuong;
    private String _MieuTa;

    public VietnameseDelicacies(String _KieuMonAn, String _TenMon, String _HinhAnh, String _DiaPhuong, String _MieuTa) {
        this._KieuMonAn = _KieuMonAn;
        this._TenMon = _TenMon;
        this._HinhAnh = _HinhAnh;
        this._DiaPhuong = _DiaPhuong;
        this._MieuTa = _MieuTa;
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
}
