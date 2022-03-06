package com.warehousing.aquarium.repository;

import com.warehousing.aquarium.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<BrandEntity, Long> {
}
