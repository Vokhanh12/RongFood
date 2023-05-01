package com.example.test.Model;

import java.util.LinkedList;

public class Store {
    private String _MaCH;
    private String _NguoiSoHu;
    private String _TenCH;

    private Location _Location;

    private LinkedList<VietnameseDelicacies> _llMenu;


    public Store(String MaCH,String NguoiSoHu,String TenCH,Location location,LinkedList<VietnameseDelicacies> llMenu){
        this._MaCH=MaCH;
        this._NguoiSoHu=NguoiSoHu;
        this._TenCH=TenCH;
        this._Location=location;
        this._llMenu=llMenu;
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


}
