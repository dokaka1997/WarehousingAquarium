package com.warehousing.aquarium.service;

import com.warehousing.aquarium.entity.CustomerEntity;

import java.util.List;

public interface CustomerService {

    CustomerEntity addNewCustomer(CustomerEntity customerEntity);

    Boolean deleteCustomer(Long id);

    CustomerEntity getCustomerById(Long id);

    List<CustomerEntity> getAllCustomer();
}
