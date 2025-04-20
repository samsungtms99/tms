package com.hunar.api.service;

import com.hunar.api.bean.AddressBean;
import com.hunar.api.exceptionHandling.util.FmkException;

import java.util.List;

public interface AddressService {
    AddressBean createAddress(AddressBean customerBean) throws FmkException;
    AddressBean updateAddress(AddressBean customerBean) throws FmkException;
    List<AddressBean> getListOfAllAddresss()throws FmkException;
    AddressBean getAddressById(int idAddress)throws FmkException;
    String deleteAddressById(int idAddress ) throws  FmkException;
    List<AddressBean> getListOfAllAddresssByCustomerId(int custId)throws FmkException;
}
