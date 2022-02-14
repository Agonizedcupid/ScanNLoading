package com.aariyan.scannloading.Model;

public class HeaderLinesModel {

    private String storeName;
    private String OrderNo;
    private String MESSAGESINV;
    private String deladdress;
    private int orderId;

    public HeaderLinesModel() {}

    public HeaderLinesModel(String storeName, String orderNo, String MESSAGESINV, String deladdress, int orderId) {
        this.storeName = storeName;
        OrderNo = orderNo;
        this.MESSAGESINV = MESSAGESINV;
        this.deladdress = deladdress;
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

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String orderNo) {
        OrderNo = orderNo;
    }

    public String getMESSAGESINV() {
        return MESSAGESINV;
    }

    public void setMESSAGESINV(String MESSAGESINV) {
        this.MESSAGESINV = MESSAGESINV;
    }

    public String getDeladdress() {
        return deladdress;
    }

    public void setDeladdress(String deladdress) {
        this.deladdress = deladdress;
    }
}
