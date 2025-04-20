package com.hunar.api.service;

import com.hunar.api.bean.CustomerBean;
import com.hunar.api.exceptionHandling.util.FmkException;

import java.util.List;

public interface CustomerService {

    CustomerBean createCustomer(CustomerBean customerBean) throws FmkException;
    CustomerBean updateCustomer(CustomerBean customerBean) throws FmkException;
    List<CustomerBean> getListOfAllCustomers()throws FmkException;
    CustomerBean getCustomerByIdOrCustomerName(String customerName)throws FmkException;
    void deleteCustomerById(int id)throws FmkException;
    List<CustomerBean> findCutomerByNameOrMobile(String name, String mobile) throws FmkException;
}
