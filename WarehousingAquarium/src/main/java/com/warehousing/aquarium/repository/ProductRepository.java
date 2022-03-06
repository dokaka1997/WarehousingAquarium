package com.warehousing.aquarium.repository;

import com.warehousing.aquarium.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
