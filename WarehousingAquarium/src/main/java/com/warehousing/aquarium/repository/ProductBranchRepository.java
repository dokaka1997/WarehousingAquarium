package com.warehousing.aquarium.repository;

import com.warehousing.aquarium.entity.ProductBranchEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductBranchRepository extends JpaRepository<ProductBranchEntity, Long> {
    List<ProductBranchEntity> findAllByImportId(Long id);

    List<ProductBranchEntity> findAllByExportId(Long id);
}
