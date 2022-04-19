package com.warehousing.aquarium.service;

import com.warehousing.aquarium.entity.CommentEntity;
import com.warehousing.aquarium.model.response.CommentDTO;
import com.warehousing.aquarium.model.response.ListCommentResponse;

public interface CommentService {
    CommentDTO addNewComment(CommentEntity commentEntity);

    ListCommentResponse getAllComment(int pageIndex, int pageSize, Long topicId);
}
