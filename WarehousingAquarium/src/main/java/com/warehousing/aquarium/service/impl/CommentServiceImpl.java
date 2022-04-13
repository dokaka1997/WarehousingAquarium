package com.warehousing.aquarium.service.impl;

import com.warehousing.aquarium.entity.CommentEntity;
import com.warehousing.aquarium.repository.CommentRepository;
import com.warehousing.aquarium.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CommentServiceImpl implements CommentService {
    CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Boolean addNewComment(CommentEntity commentEntity) {
        try {
            commentEntity.setCreatedDate(new Date());
            commentRepository.save(commentEntity);
            return true;

        } catch (Exception exception) {
            return false;
        }
    }
}
