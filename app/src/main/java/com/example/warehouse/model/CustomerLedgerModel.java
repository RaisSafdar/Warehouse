package com.example.warehouse.model;

public class CustomerLedgerModel {
    String id,date,orderid,credit,debit,vendorid;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDebit() {
        return debit;
    }

    public CustomerLedgerModel(String id, String date, String orderid,
                               String debit, String credit, String vendorid) {
        this.id = id;
        this.date = date;
        this.orderid = orderid;
        this.debit = debit;
        this.credit = credit;


        this.vendorid = vendorid;
    }

    public void setDebit(String debit) {
        this.debit = debit;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getVendorid() {
        return vendorid;
    }

    public void setVendorid(String vendorid) {
        this.vendorid = vendorid;
    }


    public CustomerLedgerModel() {
    }
}
