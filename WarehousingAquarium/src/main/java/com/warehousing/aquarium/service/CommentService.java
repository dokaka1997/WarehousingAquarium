package com.warehousing.aquarium.service;

import com.warehousing.aquarium.entity.CommentEntity;
import org.springframework.stereotype.Service;

public interface CommentService {
    Boolean addNewComment(CommentEntity commentEntity);
}
