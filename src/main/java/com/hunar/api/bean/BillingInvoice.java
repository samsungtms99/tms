package com.hunar.api.bean;

import com.hunar.api.entity.BillingEntity;
import com.hunar.api.entity.BillingMapEntity;
import com.hunar.api.entity.CompanyEntity;
import com.hunar.api.entity.Order;

import java.util.List;

public class BillingInvoice {
    private BillingEntity billingEntity;
    private List<BillingMapEntity> items;
    private CompanyEntity companyEntity;
    private  String customerName;
//    private Order order;

    public BillingEntity getBillingEntity() {
        return billingEntity;
    }

    public void setBillingEntity(BillingEntity billingEntity) {
        this.billingEntity = billingEntity;
    }

    public List<BillingMapEntity> getItems() {
        return items;
    }

    public void setItems(List<BillingMapEntity> items) {
        this.items = items;
    }

    public CompanyEntity getCompanyEntity() {
        return companyEntity;
    }

    public void setCompanyEntity(CompanyEntity companyEntity) {
        this.companyEntity = companyEntity;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    //    public Order getOrder() {
//        return order;
//    }
//
//    public void setOrder(Order order) {
//        this.order = order;
//    }

}
