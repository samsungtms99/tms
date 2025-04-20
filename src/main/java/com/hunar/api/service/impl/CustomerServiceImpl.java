package com.hunar.api.service.impl;

import com.hunar.api.bean.CustomerBean;
import com.hunar.api.bean.CustomerBean;
import com.hunar.api.entity.CustomerEntity;
import com.hunar.api.entity.CustomerEntity;
import com.hunar.api.exceptionHandling.util.Errors;
import com.hunar.api.exceptionHandling.util.FmkException;
import com.hunar.api.repository.CustomerRepository;
import com.hunar.api.service.AddressService;
import com.hunar.api.service.CustomerService;
import com.hunar.api.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    public static Logger logger = LogManager.getLogger(CustomerServiceImpl.class);

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AddressService addressService;

   private final OrderService orderService;

    @Autowired
    public CustomerServiceImpl(OrderService orderService) {
        this.orderService = orderService;
    }


    @Override
    public CustomerBean createCustomer(CustomerBean customerBean) throws FmkException {
            logger.info("Creating new customer: "+customerBean.getCustomerName());
            CustomerEntity customerEntity = new CustomerEntity();
            BeanUtils.copyProperties(customerBean,customerEntity);
           customerEntity= customerRepository.save(customerEntity);
            logger.info("Created new customer successfully: "+customerBean.getCustomerName());
            CustomerBean customerBean1 = new CustomerBean();
            BeanUtils.copyProperties(customerEntity,customerBean1);
            return  customerBean1;
    }

    @Override
    public CustomerBean updateCustomer(CustomerBean customerBean) throws FmkException {
        if (customerBean != null && customerBean.getCustomerId()!=0) {
            logger.info("Updating customer: " + customerBean.getCustomerName());
            Optional<CustomerEntity> customerEntity = customerRepository.findById(customerBean.getCustomerId());
            if (!customerEntity.isPresent()) {
                logger.info("Customer does not exists with CustomerName: " + customerBean.getCustomerName());
                throw new FmkException("C1001","Customer does not exists with Customer Id: " + customerBean.getCustomerId());
            }
            customerEntity.get().setCustomerId(customerBean.getCustomerId());
            customerEntity.get().setCustomerEmail(customerBean.getCustomerEmail());
            customerEntity.get().setMobileNo(customerBean.getMobileNo());
            customerEntity.get().setGender(customerBean.getGender());
            customerEntity.get().setCustomerName(customerBean.getCustomerName());
            customerEntity.get().setCustomerName(customerBean.getAddress());
           CustomerEntity customerEntity1 = customerRepository.save(customerEntity.get());
            logger.info("Updated customer successfully: " + customerBean.getCustomerName());
            CustomerBean customerBean1 = new CustomerBean();
            BeanUtils.copyProperties(customerEntity1,customerBean1);
            return customerBean1;
        }
        return  new CustomerBean();
    }

    @Override
    public List<CustomerBean> getListOfAllCustomers() throws FmkException {
        List<CustomerBean> listOfAllCustomersBean = null;
        List<CustomerEntity> listOfAllCustomersEntity = (List<CustomerEntity>) customerRepository.findAll();
        if (!listOfAllCustomersEntity.isEmpty()){
            listOfAllCustomersBean = new ArrayList<>();
            for (CustomerEntity customerEntity : listOfAllCustomersEntity){
                CustomerBean customerBean = new CustomerBean();
                BeanUtils.copyProperties(customerEntity,customerBean);
//                customerBean.setAddressBeans(addressService.getListOfAllAddresssByCustomerId(customerEntity.getCustomerId()));
                customerBean.setOrderBeans(orderService.getListOfAllOrdersByCustomerId(customerEntity.getCustomerId()));
                listOfAllCustomersBean.add(customerBean);
            }
            return listOfAllCustomersBean;
        }else {
            return new ArrayList<>();
        }

    }

    @Override
    public CustomerBean getCustomerByIdOrCustomerName(String customerName) throws FmkException {
        if (customerName == null){
            throw  new FmkException("C1001","Customer does not exists with Customer name: " + customerName);
        }
        CustomerEntity customerEntity = customerRepository.findByCustomerName(customerName);
        if (customerEntity== null){
            throw  new FmkException("C1001","Customer does not exists with Customer name: " + customerName);
        }else {
            CustomerBean customerBean = new CustomerBean();
            BeanUtils.copyProperties(customerEntity,customerBean);
            return customerBean;
        }
    }

    @Override
    public void deleteCustomerById(int id) throws FmkException {
        if (id == 0){
            logger.info("Invalid customer ID: "+id);
            throw  new FmkException("C1002","Invalid customer Id: " + String.valueOf(id));
        }
        customerRepository.deleteById(id);
    }

    @Override
    public List<CustomerBean> findCutomerByNameOrMobile(String name, String mobile) throws FmkException {
        List<CustomerBean> customerBeans = null;
        if (name==null && mobile==null){
            this.getListOfAllCustomers();
        }else {
          List<CustomerEntity> customer =  customerRepository.findByCustomerNameOrByMobileNo(name,mobile);
          if (!customer.isEmpty()){
              customerBeans = new ArrayList<>();
              for (CustomerEntity customerEntity:customer){
                  CustomerBean customerBean = new CustomerBean();
                  BeanUtils.copyProperties(customerEntity, customerBean);
                  customerBeans.add(customerBean);
              }
              return  customerBeans;
          }
          else {
              return new ArrayList<>();
          }
        }
        return  customerBeans;
    }
}

