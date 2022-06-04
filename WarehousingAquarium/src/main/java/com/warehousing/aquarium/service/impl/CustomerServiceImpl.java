package com.warehousing.aquarium.service.impl;

import com.warehousing.aquarium.entity.CustomerEntity;
import com.warehousing.aquarium.repository.CustomerRepository;
import com.warehousing.aquarium.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerEntity addNewCustomer(CustomerEntity customerEntity) {
        if (!customerRepository.findAllByCustomerEmailOrCustomerPhone(customerEntity.getCustomerEmail(), customerEntity.getCustomerPhone()).isEmpty()) {
            throw new RuntimeException("Email or Phone existed");
        }
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
        CustomerEntity customerEntity = new CustomerEntity();
        Optional<CustomerEntity> optionalCustomerEntity = customerRepository.findById(id);
        if (optionalCustomerEntity.isPresent()) {
            customerEntity = optionalCustomerEntity.get();
        }
        return customerEntity;
    }

    @Override
    public List<CustomerEntity> getAllCustomer() {
        return customerRepository.findAll();
    }

    @Override
    public CustomerEntity payDebtCustomerById(Long id) {
        CustomerEntity customerEntity = new CustomerEntity();
        Optional<CustomerEntity> optionalCustomerEntity = customerRepository.findById(id);
        if (optionalCustomerEntity.isPresent()) {
            customerEntity = optionalCustomerEntity.get();
            customerEntity.setDebt(0D);
            customerRepository.save(customerEntity);
        }
        return customerEntity;
    }
}
