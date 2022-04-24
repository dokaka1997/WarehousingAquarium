package com.warehousing.aquarium.repository;

import com.warehousing.aquarium.entity.ExportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExportRepository extends JpaRepository<ExportEntity, Long> {
}
