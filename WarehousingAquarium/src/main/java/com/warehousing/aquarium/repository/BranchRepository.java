package com.warehousing.aquarium.repository;

import com.warehousing.aquarium.entity.BranchEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchRepository extends JpaRepository<BranchEntity, Long> {
    BranchEntity getFirstByBranchName(String name);
}
