package com.warehousing.aquarium.repository;

import com.warehousing.aquarium.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    List<CustomerEntity> findAllByCustomerEmailOrCustomerPhone(String email, String phone);
}
