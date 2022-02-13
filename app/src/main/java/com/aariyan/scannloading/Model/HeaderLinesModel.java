package com.aariyan.scannloading.Model;

public class HeaderLinesModel {

    private String storeName;
    private int orderId;

    public HeaderLinesModel() {}

    public HeaderLinesModel(String storeName, int orderId) {
        this.storeName = storeName;
        this.orderId = orderId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
