package com.example.test.Model;

public class Menu {
    final private String name;
    final private double price;
    final private String image;
    final private int view;
    public Menu(String name, double price, String image,int view) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.view = view;
    }
    public int getView() {
        return view;
    }



    public String getName() {
        return name;
    }



    public double getPrice() {
        return price;
    }



    public String getImage() {
        return image;
    }
}

