package com.example.test.Model;

public class ShopView {

    public String NameFood;

    public String UlrImage;
    public double Price;

    public void setUlrImage(String ulrImage) {
        UlrImage = ulrImage;
    }

    public String getUlrImage() {
        return UlrImage;
    }

    public ShopView(String nameFood, double price, String ulrImage) {
        this.NameFood = nameFood;
        this.Price = price;
        this.UlrImage = ulrImage;
    }

    public void setNameFood(String nameFood) {
        NameFood = nameFood;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public String getNameFood() {
        return NameFood;
    }

    public double getPrice() {
        return Price;
    }
}
