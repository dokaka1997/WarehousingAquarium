package com.warehousing.aquarium.service;

import com.warehousing.aquarium.entity.CommentEntity;
import com.warehousing.aquarium.model.response.CommentDTO;
import org.springframework.stereotype.Service;

public interface CommentService {
    CommentDTO addNewComment(CommentEntity commentEntity);
}
