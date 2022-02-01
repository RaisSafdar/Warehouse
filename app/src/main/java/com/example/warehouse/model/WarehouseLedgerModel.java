package com.example.warehouse.model;

public class WarehouseLedgerModel {
    String id,date,orderid,credit,debit,vendorid,vendorname;

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

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getDebit() {
        return debit;
    }

    public void setDebit(String debit) {
        this.debit = debit;
    }

    public String getVendorid() {
        return vendorid;
    }

    public void setVendorid(String vendorid) {
        this.vendorid = vendorid;
    }

    public String getVendorname() {
        return vendorname;
    }

    public void setVendorname(String vendorname) {
        this.vendorname = vendorname;
    }

    public WarehouseLedgerModel(String id, String date, String orderid, String credit, String debit, String vendorid, String vendorname) {
        this.id = id;
        this.date = date;
        this.orderid = orderid;
        this.credit = credit;
        this.debit = debit;
        this.vendorid = vendorid;
        this.vendorname = vendorname;
    }

    public WarehouseLedgerModel() {
    }
}
