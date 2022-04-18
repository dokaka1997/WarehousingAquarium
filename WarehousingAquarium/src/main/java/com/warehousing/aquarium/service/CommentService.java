package com.warehousing.aquarium.service;

import com.warehousing.aquarium.entity.CommentEntity;
import com.warehousing.aquarium.model.response.CommentDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CommentService {
    CommentDTO addNewComment(CommentEntity commentEntity);

    List<CommentDTO> getAllComment(int pageIndex, int pageSize, Long topicId);
}
