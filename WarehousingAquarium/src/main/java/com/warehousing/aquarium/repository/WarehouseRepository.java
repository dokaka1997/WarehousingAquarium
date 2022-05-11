package com.warehousing.aquarium.repository;

import com.warehousing.aquarium.entity.WarehouseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface WarehouseRepository extends JpaRepository<WarehouseEntity, Long> {


    List<WarehouseEntity> findAllByProductId(Long productId);

}
