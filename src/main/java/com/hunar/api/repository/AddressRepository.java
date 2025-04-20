package com.hunar.api.repository;

import com.hunar.api.entity.Address;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AddressRepository extends CrudRepository<Address, Integer> {
    List<Address> findAllByIdCustomer(int custId);
}
