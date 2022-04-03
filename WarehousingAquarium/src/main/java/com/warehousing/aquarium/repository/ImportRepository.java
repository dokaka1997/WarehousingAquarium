package com.warehousing.aquarium.repository;

import com.warehousing.aquarium.entity.ImportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImportRepository extends JpaRepository<ImportEntity, Long> {
    List<ImportEntity> findAllByImportIDIsLike(String value);
}
