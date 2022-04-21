package com.warehousing.aquarium.service.impl;

import com.warehousing.aquarium.entity.CustomerEntity;
import com.warehousing.aquarium.repository.CustomerRepository;
import com.warehousing.aquarium.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerEntity addNewCustomer(CustomerEntity customerEntity) {
        return customerRepository.save(customerEntity);
    }

    @Override
    public Boolean deleteCustomer(Long id) {
        try {
            customerRepository.deleteById(id);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    @Override
    public CustomerEntity getCustomerById(Long id) {
        return customerRepository.findById(id).get();
    }

    @Override
    public List<CustomerEntity> getAllCustomer() {
        return customerRepository.findAll();
    }
}
