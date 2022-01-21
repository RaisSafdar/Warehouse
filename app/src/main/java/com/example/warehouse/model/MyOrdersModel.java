package com.example.warehouse.model;

import android.content.Context;

import java.util.List;

public class MyOrdersModel {
    String id,date,delivery, city , percentage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getDelivery() {
        return delivery;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public MyOrdersModel(String id, String date, String delivery, String city, String percentage)  {
        this.id = id;
        this.date = date;
        this.delivery = delivery;
        this.city = city;
        this.percentage = percentage;
    }

    public MyOrdersModel(List<MyOrdersModel> list, Context applicationContext) {
    }
}
