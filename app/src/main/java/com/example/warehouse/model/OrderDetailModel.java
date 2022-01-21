package com.example.warehouse.model;

public class OrderDetailModel {
    String id,order_id,product_name,product_image,total,quantity,purchase_price,status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPurchase_price() {
        return purchase_price;
    }

    public void setPurchase_price(String purchase_price) {
        this.purchase_price = purchase_price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public OrderDetailModel(String id, String order_id, String product_name, String product_image, String total, String quantity, String purchase_price, String status) {
        this.id = id;
        this.order_id = order_id;
        this.product_name = product_name;
        this.product_image = product_image;
        this.total = total;
        this.quantity = quantity;
        this.purchase_price = purchase_price;
        this.status = status;
    }

    public OrderDetailModel() {
    }
}
