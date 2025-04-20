package com.hunar.api.bean;


import jakarta.persistence.Column;

import java.util.ArrayList;
import java.util.List;

public class BilingBean {

    private int idBill;
    private int idCustomer;
    private int idOrder;
    private String orderNo;
    private String invoiceNo;
    private double totalAmt;
    private double advanceAmt;
    private double remAmt;
    private double discountedAmt;
    private double actualAmt;
    private String paymentStatus;
    private String remarks;
    private List<BillingMapBean> billingMap = new ArrayList<>();

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getIdBill() {
        return idBill;
    }

    public void setIdBill(int idBill) {
        this.idBill = idBill;
    }

    public double getActualAmt() {
        return actualAmt;
    }

    public void setActualAmt(double actualAmt) {
        this.actualAmt = actualAmt;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public double getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(double totalAmt) {
        this.totalAmt = totalAmt;
    }

    public double getAdvanceAmt() {
        return advanceAmt;
    }

    public void setAdvanceAmt(double advanceAmt) {
        this.advanceAmt = advanceAmt;
    }

    public double getRemAmt() {
        return remAmt;
    }

    public void setRemAmt(double remAmt) {
        this.remAmt = remAmt;
    }

    public double getDiscountedAmt() {
        return discountedAmt;
    }

    public void setDiscountedAmt(double discountedAmt) {
        this.discountedAmt = discountedAmt;
    }

    public List<BillingMapBean> getBillingMap() {
        return billingMap;
    }

    public void setBillingMap(List<BillingMapBean> billingMap) {
        this.billingMap = billingMap;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
