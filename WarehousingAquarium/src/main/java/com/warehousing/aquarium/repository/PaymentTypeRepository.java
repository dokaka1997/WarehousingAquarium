package com.warehousing.aquarium.repository;

import com.warehousing.aquarium.entity.PaymentTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentTypeRepository extends JpaRepository<PaymentTypeEntity, Long> {
    PaymentTypeEntity getFirstByPaymentName(String name);
}
