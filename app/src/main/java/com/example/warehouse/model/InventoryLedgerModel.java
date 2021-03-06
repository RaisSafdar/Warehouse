package com.example.warehouse.model;

public class InventoryLedgerModel {
    String id,product_name,sale_price
            ,remaining,product_price;

    public InventoryLedgerModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getRemaining() {
        return remaining;
    }

    public void setRemaining(String remaining) {
        this.remaining = remaining;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public InventoryLedgerModel(String id, String product_name, String sale_price, String remaining, String product_price) {
        this.id = id;
        this.product_name = product_name;
        this.sale_price = sale_price;
        this.remaining = remaining;
        this.product_price = product_price;
    }
}
