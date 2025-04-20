package com.hunar.api.entity;

import com.hunar.api.generic.entity.GenericEntity;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "TM_CUSTOMER")
public class CustomerEntity extends GenericEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "ID_CUSTOMER")
        private int customerId;

        @Column(name = "CUSTOMER_NAME")
        private String customerName;

        @Column(name = "NAME_EMAIL", unique = true)
        private String customerEmail;

        @Column(name = "MOBILE_NO")
        private String mobileNo;

        @Column(name = "GENDER")
        private String gender;

        @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
        private List<Order> bookings;

        @Column(name = "ADDRESS")
        private String address;

//        @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
//        private List<TypeMeasurement> typeMeasurements;

    public  CustomerEntity(){
        super();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<Order> getBookings() {
        return bookings;
    }

    public void setBookings(List<Order> bookings) {
        this.bookings = bookings;
    }

//    public List<Address> getAddresses() {
//        return addresses;
//    }
//
//    public void setAddresses(List<Address> addresses) {
//        this.addresses = addresses;
//    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
