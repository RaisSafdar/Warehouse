package com.example.warehouse.model;

public class InventoryLedgerModel {
    String id,date,orderid,product_name,sale_price,
            sale_quantity,received_quantity,received_amount,product_price;

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

    public String getSale_quantity() {
        return sale_quantity;
    }

    public void setSale_quantity(String sale_quantity) {
        this.sale_quantity = sale_quantity;
    }

    public String getReceived_quantity() {
        return received_quantity;
    }

    public void setReceived_quantity(String received_quantity) {
        this.received_quantity = received_quantity;
    }

    public String getReceived_amount() {
        return received_amount;
    }

    public void setReceived_amount(String received_amount) {
        this.received_amount = received_amount;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public InventoryLedgerModel(String id, String date, String orderid, String product_name, String sale_price, String sale_quantity, String received_quantity, String received_amount, String product_price) {
        this.id = id;
        this.date = date;
        this.orderid = orderid;
        this.product_name = product_name;
        this.sale_price = sale_price;
        this.sale_quantity = sale_quantity;
        this.received_quantity = received_quantity;
        this.received_amount = received_amount;
        this.product_price = product_price;
    }

    public InventoryLedgerModel() {
    }
}
