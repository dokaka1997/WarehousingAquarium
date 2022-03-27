package com.warehousing.aquarium.repository;

import com.warehousing.aquarium.entity.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository  extends JpaRepository<StatusEntity, Long> {
}
