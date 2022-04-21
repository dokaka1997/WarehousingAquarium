package com.warehousing.aquarium.controller;

import com.warehousing.aquarium.entity.CustomerEntity;
import com.warehousing.aquarium.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("customer")
public class CustomerController {

    CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerEntity> addNewCustomer(@RequestBody CustomerEntity customerEntity) {
        return ResponseEntity.ok(customerService.addNewCustomer(customerEntity));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerEntity> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @GetMapping()
    public ResponseEntity<List<CustomerEntity>> getAllCustomer() {
        return ResponseEntity.ok(customerService.getAllCustomer());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.deleteCustomer(id));
    }

}
