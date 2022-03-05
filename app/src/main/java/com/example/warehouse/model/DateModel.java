package com.example.warehouse.model;

public class DateModel {
    String id, vid, date, vname;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVname() {
        return vname;
    }

    public void setVname(String vname) {
        this.vname = vname;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public DateModel(String id, String vid, String date, String vname) {
        this.id = id;
        this.vid = vid;
        this.date = date;
        this.vname = vname;
    }


}
