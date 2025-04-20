package com.hunar.api.repository;

import com.hunar.api.entity.CustomerEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<CustomerEntity, Integer> {
    CustomerEntity findByCustomerName(String customerName);

    @Query("select c from CustomerEntity c where c.customerName = %:name% or c.mobileNo like %:mobile%")
    List<CustomerEntity> findByCustomerNameOrByMobileNo(String name, String mobile);

}
