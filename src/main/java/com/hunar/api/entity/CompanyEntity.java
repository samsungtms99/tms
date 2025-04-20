package com.hunar.api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "TM_COMPANY")
public class CompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String companyName;
    private String companyAddress;
    private String city;
    private String pincode;
    private String companyMblNo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCompanyMblNo() {
        return companyMblNo;
    }

    public void setCompanyMblNo(String companyMblNo) {
        this.companyMblNo = companyMblNo;
    }
}
