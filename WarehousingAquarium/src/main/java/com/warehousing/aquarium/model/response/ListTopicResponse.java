package com.warehousing.aquarium.model.response;

import lombok.Data;

import java.util.List;

@Data
public class ListTopicResponse {
    private List<TopicDTO> topics;
    private int total;
}
