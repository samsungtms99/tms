package com.hunar.api.entity;

import com.hunar.api.generic.entity.GenericEntity;
import jakarta.persistence.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Entity
@Table(name = "TM_ORDER")
public class Order extends GenericEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int orderId;

    private String orderNo;

    private LocalDate bookingDate;

    private LocalDate deliveryDate;

    private LocalDate actualDeliveryDate;

    private LocalDate completionDate;

    private String comments;

    private String type; // pant, shirt etc

    private double amount;

    private double totalAmt;

    private int quantity;

    private String customerName;

    private String alterComments;

    private String paymentStatus;
//    private String dart_point;
//
//    private String waist_length;
//
//    private  String length_of_shirt;
//
//    private String upper_bust;
//
//    private String bust;
//
//    private String belly;
//
//    private String waist;
//
//    private String hip;
//
//    private String shoulders;
//
//    private String sleeves_length_width;
//
//
//    private String arm_hole;
//
//    private String biceps;
//
//    private String length_of_pant;
//
//    private String waist_of_pants;
//
//
//    private String thighs;
//
//    private String calfs;
//
//    private String knees;
//
//    private String round_bottom;

    private  String orderStatus;

//    @Lob
//    private byte[] image;

//    @Column(name = "ID_ADDRESS", insertable = false,updatable = false)
//    private int idAddress;

    @Column(name = "ID_CUSTOMER", insertable = false,updatable = false)
    private int idCustomer;

//    @OneToOne
//    @JoinColumn(name = "ID_ADDRESS")
//    private  Address address;

    @ManyToOne
    @JoinColumn(name = "ID_CUSTOMER")
    private CustomerEntity customer;

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

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

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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
