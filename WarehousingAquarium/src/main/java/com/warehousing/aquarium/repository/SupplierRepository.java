package com.warehousing.aquarium.repository;

import com.warehousing.aquarium.entity.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplierRepository extends JpaRepository<SupplierEntity, Long> {

    SupplierEntity getFirstBySupplierName(String name);

    List<SupplierEntity> findAllByEmailOrPhone(String email, String phone);

}
