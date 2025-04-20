package com.hunar.api.controller;

import com.hunar.api.bean.AddressBean;
import com.hunar.api.constant.Constants;
import com.hunar.api.exceptionHandling.util.FmkException;
import com.hunar.api.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = Constants.CROSS_ORIGIN)
@RestController
@RequestMapping("api/address")
public class AddressController {

    @Autowired
    AddressService addressService;

    @PostMapping(value = "/createAddress")
    public AddressBean createAddress(@RequestBody AddressBean addressBean) throws FmkException {
        if (addressBean!=null){
           return addressService.createAddress(addressBean);
        }
        return null;
    }

    @PostMapping(value = "/updateAddress")
    public AddressBean updateAddress(@RequestBody AddressBean addressBean) throws FmkException {
        if (addressBean!=null){
           return addressService.updateAddress(addressBean);
        }
        return null;
    }

    @GetMapping(value = "/getListOfAllAddresss")
    List<AddressBean> getListOfAllAddresss()throws FmkException{
        return  addressService.getListOfAllAddresss();
    }

    @GetMapping(value = "/getListOfAllAddresssByCustomerId/{idCustomer}")
    List<AddressBean> getListOfAllAddresssByCustomerId(@PathVariable("idCustomer") int idCustomer)throws FmkException{
        return  addressService.getListOfAllAddresssByCustomerId(idCustomer);
    }

    @GetMapping(value = "/getAddressByAddressName/{idAddress}")
    AddressBean getAddressByAddressName(@PathVariable("idAddress") int idAddress)throws FmkException{
        return  addressService.getAddressById(idAddress);
    }

    @DeleteMapping(value = "/deleteAddressById/{id}")
    String deleteAddressById(@PathVariable("id") int id)throws FmkException{
       return addressService.deleteAddressById(id);
    }
}
