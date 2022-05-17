package com.warehousing.aquarium.repository;

import com.warehousing.aquarium.entity.ProductBatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface WarehouseRepository extends JpaRepository<ProductBatchEntity, Long> {


    List<ProductBatchEntity> findAllByProductId(Long productId);

}
