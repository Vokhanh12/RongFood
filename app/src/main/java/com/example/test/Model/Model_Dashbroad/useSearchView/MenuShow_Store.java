package com.example.test.Model.Model_Dashbroad.useSearchView;

public class MenuShow_Store {

    private String _tenMonAn;
    private String _tenCuaHang;

    public MenuShow_Store(String NameMenu ,String type){
        if(type.equals("NameFood"))
            this._tenMonAn = NameMenu;
        else if(type.equals("NameStore"))
            this._tenCuaHang = NameMenu;
    }


    //Getter and Setter

    public void set_tenMonAn(String _tenMonAn) {
        this._tenMonAn = _tenMonAn;
    }

    public void set_tenCuaHang(String _tenCuaHang) {
        this._tenCuaHang = _tenCuaHang;
    }

    public String get_tenMonAn() {
        return _tenMonAn;
    }

    public String get_tenCuaHang() {
        return _tenCuaHang;
    }
}
