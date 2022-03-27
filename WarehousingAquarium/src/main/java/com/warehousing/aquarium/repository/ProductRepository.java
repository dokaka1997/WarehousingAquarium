package com.warehousing.aquarium.repository;

import com.warehousing.aquarium.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {


    List<ProductEntity> findAllByBarCodeOrProductCodeOrProductName(String search, String search2, String search3);

    List<ProductEntity> findAllByBarCodeContainingOrProductCodeContainingOrProductNameContaining(String search, String search2, String search3);

    ProductEntity getFirstByProductName(String name);
}
