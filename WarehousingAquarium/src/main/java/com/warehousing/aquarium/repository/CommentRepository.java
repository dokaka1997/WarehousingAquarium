package com.warehousing.aquarium.repository;

import com.warehousing.aquarium.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
}
