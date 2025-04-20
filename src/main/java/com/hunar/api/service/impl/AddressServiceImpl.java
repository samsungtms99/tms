package com.hunar.api.service.impl;

import com.hunar.api.bean.AddressBean;
import com.hunar.api.bean.AddressBean;
import com.hunar.api.entity.Address;
import com.hunar.api.entity.CustomerEntity;
import com.hunar.api.exceptionHandling.util.Errors;
import com.hunar.api.exceptionHandling.util.FmkException;
import com.hunar.api.repository.AddressRepository;
import com.hunar.api.repository.CustomerRepository;
import com.hunar.api.service.AddressService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    public static Logger logger = LogManager.getLogger(AddressServiceImpl.class);

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public AddressBean createAddress(AddressBean addressBean) throws FmkException {
        logger.info("Creating new address: "+addressBean.toString());
        Address addressEntity = new Address();
        BeanUtils.copyProperties(addressBean,addressEntity);
        Optional<CustomerEntity> customer = customerRepository.findById(addressBean.getIdCustomer());
        if (!customer.isPresent()){
            throw new FmkException("C1001", "Invalid customer id: "+ String.valueOf(addressBean.getIdCustomer()));
        }
        addressEntity.setCustomer(customer.get());
       addressEntity= addressRepository.save(addressEntity);
        logger.info("Created new address successfully: "+addressBean.toString());
        AddressBean addressBean1 = new AddressBean();
        BeanUtils.copyProperties(addressEntity,addressBean1);
        return addressBean1;
    }

    @Override
    public AddressBean updateAddress(AddressBean addressBean) throws FmkException {
        if (addressBean != null && addressBean.getIdAddress()!=0) {
            logger.info("Updating address: " + addressBean.getIdAddress());
            Optional<Address> addressEntity = addressRepository.findById(addressBean.getIdAddress());
            if (!addressEntity.isPresent()) {
                logger.info("Address does not exists with Address Id: " + addressBean.getIdAddress());
                throw new FmkException("A1001","Address does not exists with Address Id: " + addressBean.getIdAddress());
            }
            addressEntity.get().setIdAddress(addressBean.getIdAddress());
            addressEntity.get().setAddressLine(addressBean.getAddressLine());
            addressEntity.get().setCity(addressBean.getCity());
            addressEntity.get().setCountry(addressBean.getCountry());
            addressEntity.get().setState(addressBean.getState());
            addressEntity.get().setPinCode(addressBean.getPinCode());
           Address address= addressRepository.save(addressEntity.get());
            logger.info("Updated address successfully: " + addressBean.getIdAddress());
            AddressBean addressBean1 = new AddressBean();
            BeanUtils.copyProperties(address,addressBean1);
            return addressBean1;
        }
        return null;
    }

    @Override
    public List<AddressBean> getListOfAllAddresss() throws FmkException {
        List<AddressBean> listOfAllAddresssBean = null;
        List<Address> listOfAllAddresssEntity = (List<Address>) addressRepository.findAll();
        if (!listOfAllAddresssEntity.isEmpty()){
            listOfAllAddresssBean = new ArrayList<>();
            for (Address addressEntity : listOfAllAddresssEntity){
                AddressBean addressBean = new AddressBean();
                BeanUtils.copyProperties(addressEntity,addressBean);
                listOfAllAddresssBean.add(addressBean);
            }
        }
        return listOfAllAddresssBean;
    }

    @Override
    public AddressBean getAddressById(int idAddress) throws FmkException {
        if (idAddress <= 0){
            throw  new FmkException("A1002","Invalid Address Id: " + idAddress);
        }
        Optional<Address> addressEntity = addressRepository.findById(idAddress);
        if (!addressEntity.isPresent()){
            throw  new FmkException("A1001","Address does not exists with Address Id: " + idAddress);
        }else {
            AddressBean addressBean = new AddressBean();
            BeanUtils.copyProperties(addressEntity,addressBean);
            return addressBean;
        }
    }

    @Override
    public String deleteAddressById(int idAddress) throws FmkException {
        if (idAddress == 0){
            logger.info("Invalid address ID: "+idAddress);
            throw  new FmkException("A1002", "Invalid Address Id: "+idAddress);
        }
        addressRepository.deleteById(idAddress);
        return "Address deleted successfully";
    }

    @Override
    public List<AddressBean> getListOfAllAddresssByCustomerId(int custId) throws FmkException {
        List<AddressBean> listOfAllAddresssBean = null;
        List<Address> listOfAllAddresssEntity = (List<Address>) addressRepository.findAllByIdCustomer(custId);
        if (!listOfAllAddresssEntity.isEmpty()){
            listOfAllAddresssBean = new ArrayList<>();
            for (Address addressEntity : listOfAllAddresssEntity){
                AddressBean addressBean = new AddressBean();
                BeanUtils.copyProperties(addressEntity,addressBean);
                listOfAllAddresssBean.add(addressBean);
            }
            return listOfAllAddresssBean;
        }
        return new ArrayList<>();
    }
}
