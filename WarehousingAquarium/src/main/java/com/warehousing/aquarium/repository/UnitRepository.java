package com.warehousing.aquarium.repository;

import com.warehousing.aquarium.entity.UnitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitRepository extends JpaRepository<UnitEntity, Long> {
}
