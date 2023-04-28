package com.example.test.Model;

public class Store {
    private String _MaCH;
    private String _NguoiSoHu;
    private String _TenCH;

    private Location _Location;

    private VietnameseDelicacies _Menu;


    public Store(String MaCH,String NguoiSoHu,String TenCH,Location location,VietnameseDelicacies Menu){
        this._MaCH=MaCH;
        this._NguoiSoHu=NguoiSoHu;
        this._TenCH=TenCH;
        this._Location=location;
        this._Menu=Menu;
    }

    public VietnameseDelicacies get_Menu() {
        return _Menu;
    }

    public void set_Menu(VietnameseDelicacies _Menu) {
        this._Menu = _Menu;
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


}
