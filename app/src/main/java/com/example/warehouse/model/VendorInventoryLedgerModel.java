package com.example.warehouse.model;

public class VendorInventoryLedgerModel {
    String id,date,product_name,sale_price
            ,received_quantity,product_price;

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

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getSale_price() {
        return sale_price;
    }

    public void setSale_price(String sale_price) {
        this.sale_price = sale_price;
    }

    public String getReceived_quantity() {
        return received_quantity;
    }

    public void setReceived_quantity(String received_quantity) {
        this.received_quantity = received_quantity;
    }


    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public VendorInventoryLedgerModel(String id, String date, String product_name, String sale_price,
                                      String received_quantity, String product_price) {
        this.id = id;
        this.date = date;
        this.product_name = product_name;
        this.sale_price = sale_price;
        this.received_quantity = received_quantity;
        this.product_price = product_price;
    }

    public VendorInventoryLedgerModel() {
    }
}
