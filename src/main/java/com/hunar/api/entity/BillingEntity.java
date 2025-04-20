package com.hunar.api.entity;

import com.hunar.api.generic.entity.GenericEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "TM_BILLING")
public class BillingEntity extends GenericEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_billing")
    private int idBill;

    @Column(name = "id_customer")
    private int idCustomer;

    @Column(name = "id_order")
    private int idOrder;

    @Column(name = "order_num")
    private String orderNo;

    @Column(name = "invoice_num")
    private String invoiceNo;

    @Column(name = "total_amt")
    private double totalAmt;

    @Column(name = "advance_amt")
    private double advanceAmt;

    @Column(name = "remaining_amt")
    private double remAmt;

    @Column(name = "discounted_amt")
    private double discountedAmt;

    @Column(name = "actual_amt")
    private double actualAmt;

    @Column(name = "payment_status")
    private String paymentStatus;

    @Column(name = "remarks")
    private String remarks;

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

    public double getActualAmt() {
        return actualAmt;
    }

    public void setActualAmt(double actualAmt) {
        this.actualAmt = actualAmt;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
