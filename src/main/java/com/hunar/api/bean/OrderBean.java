package com.hunar.api.bean;

import com.hunar.api.generic.bean.GenericBean;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderBean extends GenericBean {

    private int orderId;

    private String orderNo;

    private LocalDate bookingDate;

    private LocalDate deliveryDate;

    private String comments;

    private String type; // pant, shirt etc

    private double amount;

    private double totalAmt;


    private int quantity;

    private int idCustomer;

    private String customerName;

    private  String orderStatus;

    List<Integer> idMeasurements = new ArrayList<>();

    private String alterComments;

    private List<TypeMeasurementBean> typeMeasurementBeans = new ArrayList<>();

    private String paymentStatus;

    private LocalDate actualDeliveryDate;

    private LocalDate completionDate;


//    private MultipartFile image;


    public String getAlterComments() {
        return alterComments;
    }

    public void setAlterComments(String alterComments) {
        this.alterComments = alterComments;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(double totalAmt) {
        this.totalAmt = totalAmt;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<Integer> getIdMeasurements() {
        return idMeasurements;
    }

    public void setIdMeasurements(List<Integer> idMeasurements) {
        this.idMeasurements = idMeasurements;
    }

    public List<TypeMeasurementBean> getTypeMeasurementBeans() {
        return typeMeasurementBeans;
    }

    public void setTypeMeasurementBeans(List<TypeMeasurementBean> typeMeasurementBeans) {
        this.typeMeasurementBeans = typeMeasurementBeans;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public LocalDate getActualDeliveryDate() {
        return actualDeliveryDate;
    }

    public void setActualDeliveryDate(LocalDate actualDeliveryDate) {
        this.actualDeliveryDate = actualDeliveryDate;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }
}
