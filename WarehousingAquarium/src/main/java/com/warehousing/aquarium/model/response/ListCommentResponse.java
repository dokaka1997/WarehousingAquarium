package com.warehousing.aquarium.model.response;

import lombok.Data;

import java.util.List;

@Data
public class ListCommentResponse {
    private int total;
    private List<CommentDTO> comments;
}
