package com.example.warehouse.model;

public class DateModel2 {
    String id, vid, date, vname,status;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DateModel2(String id, String vid, String date, String vname, String status) {
        this.id = id;
        this.vid = vid;
        this.date = date;
        this.vname = vname;
        this.status = status;
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



}
