package com.warehousing.aquarium.repository;

import com.warehousing.aquarium.entity.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<TopicEntity, Long> {
}
