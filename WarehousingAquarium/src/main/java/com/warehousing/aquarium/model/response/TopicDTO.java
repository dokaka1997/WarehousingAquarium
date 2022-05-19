package com.warehousing.aquarium.model.response;

import lombok.Data;

import java.util.Date;

@Data
public class TopicDTO implements Comparable<TopicDTO> {
    private Long topicId;
    private String createBy;
    private Date createdDate;
    private String title;
    private String assignTo;

    @Override
    public int compareTo(TopicDTO o) {
        return o.getCreatedDate().compareTo(this.createdDate);
    }
}
