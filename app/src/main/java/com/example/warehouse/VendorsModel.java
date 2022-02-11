package com.example.warehouse;

public class VendorsModel {
    String VendorCode, Status, Orderid, vendor_id,vendoritems;

    public String getVendorCode() {
        return VendorCode;
    }

    public void setVendorCode(String vendorCode) {
        VendorCode = vendorCode;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getOrderid() {
        return Orderid;
    }

    public void setOrderid(String orderid) {
        Orderid = orderid;
    }

    public String getVendor_id() {
        return vendor_id;
    }

    public void setVendor_id(String vendor_id) {
        this.vendor_id = vendor_id;
    }

    public String getVendoritems() {
        return vendoritems;
    }

    public void setVendoritems(String vendoritems) {
        this.vendoritems = vendoritems;
    }

    public VendorsModel(String vendorCode, String status, String orderid, String vendor_id, String vendoritems) {
        VendorCode = vendorCode;
        Status = status;
        Orderid = orderid;
        this.vendor_id = vendor_id;
        this.vendoritems = vendoritems;
    }

    public VendorsModel() {
    }
}
