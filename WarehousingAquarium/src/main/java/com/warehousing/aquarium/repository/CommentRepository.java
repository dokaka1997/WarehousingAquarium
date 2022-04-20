package com.warehousing.aquarium.repository;

import com.warehousing.aquarium.entity.CommentEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    List<CommentEntity> findAllByTopicId(Long topicId, Pageable pageable);

    List<CommentEntity> findAllByTopicId(Long topicId);

}
