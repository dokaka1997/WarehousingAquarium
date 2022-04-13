package com.warehousing.aquarium.model.response;

import lombok.Data;

import java.util.Date;

@Data
public class TopicDTO {
    private Long topicId;
    private String createBy;
    private Date createdDate;
    private String title;
    private String assignTo;
}
