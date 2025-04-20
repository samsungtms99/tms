package com.hunar.api.bean;

import com.hunar.api.entity.Address;
import com.hunar.api.generic.bean.GenericBean;
import jakarta.persistence.Column;

import java.util.List;

public class CustomerBean extends GenericBean {

    private int customerId;

    private String customerName;

    private String customerEmail;

    private String mobileNo;

    private String gender;

    private String address;

    private List<OrderBean> orderBeans;


    public CustomerBean() {
        super();
    }

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<OrderBean> getOrderBeans() {
        return orderBeans;
    }

    public void setOrderBeans(List<OrderBean> orderBeans) {
        this.orderBeans = orderBeans;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
