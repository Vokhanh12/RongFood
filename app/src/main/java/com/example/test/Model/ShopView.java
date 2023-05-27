package com.example.test.Model;

public class ShopView {

    public String _dishName;
    public String _ulrImage;
    public String _local;
    public String _cuisineType;
    public String _Description;
    public double _price;

    //Constructor
    public ShopView(String DishName, double Price, String UlrImage) {
        _dishName = DishName;
        _price = Price;
        _ulrImage = UlrImage;
    }

    public ShopView(String _dishName, String _ulrImage, String _local, String _cuisineType, String _Description, double _price) {
        this._dishName = _dishName;
        this._ulrImage = _ulrImage;
        this._local = _local;
        this._cuisineType = _cuisineType;
        this._Description = _Description;
        this._price = _price;
    }
    //Getter and Setter
    public String get_dishName() {
        return _dishName;
    }

    public String get_ulrImage() {
        return _ulrImage;
    }

    public String get_local() {
        return _local;
    }

    public String get_cuisineType() {
        return _cuisineType;
    }

    public String get_Description() {
        return _Description;
    }

    public double get_price() {
        return _price;
    }

    public void set_dishName(String _dishName) {
        this._dishName = _dishName;
    }

    public void set_ulrImage(String _ulrImage) {
        this._ulrImage = _ulrImage;
    }

    public void set_local(String _local) {
        this._local = _local;
    }

    public void set_cuisineType(String _cuisineType) {
        this._cuisineType = _cuisineType;
    }

    public void set_Description(String _Description) {
        this._Description = _Description;
    }

    public void set_price(double _price) {
        this._price = _price;
    }
}
