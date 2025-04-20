package com.hunar.api.controller;

import com.hunar.api.bean.CustomerBean;
import com.hunar.api.bean.CustomerBean;
import com.hunar.api.constant.Constants;
import com.hunar.api.exceptionHandling.util.FmkException;
import com.hunar.api.service.CustomerService;
import com.hunar.api.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = Constants.CROSS_ORIGIN)
@RestController
@RequestMapping("api/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping(value = "/createCustomer")
    public CustomerBean createCustomer(@RequestBody CustomerBean customerBean) throws FmkException {
        if (customerBean != null) {
           return customerService.createCustomer(customerBean);
        }
        return null;
    }

    @PostMapping(value = "/updateCustomer")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    public CustomerBean updateCustomer(@RequestBody CustomerBean customerBean) throws FmkException {
        if (customerBean != null) {
           return customerService.updateCustomer(customerBean);
        }
        return null;
    }

    @GetMapping(value = "/getListOfAllCustomers")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    List<CustomerBean> getListOfAllCustomers() throws FmkException {
        return customerService.getListOfAllCustomers();
    }

    @GetMapping(value = "/getCustomerByCustomerName/{customerName}")
    CustomerBean getCustomerByCustomerName(@PathVariable("customerName") String customerName) throws FmkException {
        return customerService.getCustomerByIdOrCustomerName(customerName);
    }

    @DeleteMapping(value = "/deleteCustomerById/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    void deleteCustomerById(@PathVariable("id") int id) throws FmkException {
        customerService.deleteCustomerById(id);
    }

    @GetMapping("/findCutomerByNameOrMobile")
    List<CustomerBean> findCutomerByNameOrMobile(@RequestParam(required = false) String name, @RequestParam(required = false) String mobile) throws FmkException {
        return  customerService.findCutomerByNameOrMobile(name, mobile);
    }
}
