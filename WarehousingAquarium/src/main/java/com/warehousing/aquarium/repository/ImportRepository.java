package com.warehousing.aquarium.repository;

import com.warehousing.aquarium.entity.ImportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImportRepository extends JpaRepository<ImportEntity, Long> {
}
