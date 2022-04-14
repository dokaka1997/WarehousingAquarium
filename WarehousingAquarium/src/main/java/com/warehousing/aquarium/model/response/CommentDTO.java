package com.warehousing.aquarium.model.response;

import lombok.Data;

import java.util.Date;
@Data
public class CommentDTO {
    private Long commentId;
    private Long topicId;
    private String content;
    private Date createdDate;
    private Long createBy;
    private String file;
}
