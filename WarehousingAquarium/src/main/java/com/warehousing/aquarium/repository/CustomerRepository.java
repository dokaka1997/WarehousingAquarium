package com.warehousing.aquarium.repository;

import com.warehousing.aquarium.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository  extends JpaRepository<CustomerEntity, Long> {
}
