package com.example.warehouse.model;

public class FulfilProductModel {
    String image, name, qty,rate;

    public FulfilProductModel() {
    }

    public FulfilProductModel(String image, String name, String qty,String rate) {
        this.image = image;
        this.name = name;
        this.qty = qty;
        this.rate = rate;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
