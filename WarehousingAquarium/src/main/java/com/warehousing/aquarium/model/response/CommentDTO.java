package com.warehousing.aquarium.model.response;

import lombok.Data;

import java.util.Date;

@Data
public class CommentDTO implements Comparable<CommentDTO> {
    private Long commentId;
    private Long topicId;
    private String content;
    private Date createdDate;
    private String createBy;
    private String file;

    @Override
    public int compareTo(CommentDTO o) {
        return this.createdDate.compareTo(o.getCreatedDate());
    }
}
